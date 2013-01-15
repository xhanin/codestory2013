package cs13.jjrental;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * User: xavierhanin
 * Date: 1/12/13
 * Time: 1:52 PM
 */
public class JJRentalOptimizer {
    public JJOptimization optimize(Collection<TripOrder> tripOrders) {
        if (tripOrders.size() <= 1) {
            return new JJOptimization(Lists.newArrayList(tripOrders));
        }

        List<TripOrder> orders = Lists.newArrayList(tripOrders);
        Collections.sort(orders);
        List<TripOrderEdge> edges = Lists.newArrayListWithCapacity(orders.size());
        for (int i = 0; i < orders.size(); i++) {
            edges.add(new TripOrderEdge(orders.get(i), getPredecessors(orders.get(i), edges)));
        }

        TripOrderEdge best = doOptimize(edges);

        return new JJOptimization(buildPath(best));
    }

    private List<TripOrder> buildPath(TripOrderEdge edge) {
        List<TripOrder> path = Lists.newLinkedList();

        for (TripOrderEdge current = edge; current != null; current = current.getPredecessor()) {
            path.add(0, current.getOrder());
        }

        return path;
    }

    private List<TripOrderEdge> getPredecessors(TripOrder tripOrder, List<TripOrderEdge> edges) {
        List<TripOrderEdge> predecessors = Lists.newArrayList();
        for (int i = edges.size() - 1; i >= 0; i--) {
            TripOrderEdge edge = edges.get(i);
            if (edge.getOrder().isCompatibleWith(tripOrder)) {
                predecessors.add(edge);
            }
        }
        return predecessors;
    }

    private TripOrderEdge doOptimize(List<TripOrderEdge> orders) {
        TripOrderEdge best = null;
        for (int i = orders.size() - 1; i >= 0; i--) {
            TripOrderEdge order = orders.get(i);
            doOptimize(order);

            if (best == null || order.getGain() > best.getGain()) {
                best = order;
            }
        }
        return best;
    }

    private void doOptimize(TripOrderEdge order) {
        if (order.getGain() > 0) {
            // edge already optimized
            return;
        }

        if (order.getPredecessors().isEmpty()) {
            order.setGain(order.getOrder().getCost());
            return;
        }

        for (TripOrderEdge predecessor : order.getPredecessors()) {
            doOptimize(predecessor);

            long potentialGain = predecessor.getGain() + order.getOrder().getCost();
            if (potentialGain > order.getGain()) {
                order.setGain(potentialGain);
                order.setPredecessor(predecessor);
            }
        }
    }
}
