package cs13.jjrental;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

import java.util.Collection;
import java.util.List;

/**
 * User: xavierhanin
 * Date: 1/12/13
 * Time: 1:52 PM
 */
public class JJRentalOptimizer {
    public JJOptimization optimize(Collection<TripOrder> tripOrders) {
        Preconditions.checkNotNull(tripOrders);
        if (tripOrders.size() <= 1) {
            return new JJOptimization(Lists.newArrayList(tripOrders));
        }

        List<TripOrderEdge> edges = Lists.newArrayListWithCapacity(tripOrders.size());
        for (TripOrder order : Ordering.natural().sortedCopy(tripOrders)) {
            edges.add(new TripOrderEdge(order, getPredecessors(order, edges)));
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
        for (TripOrderEdge order : orders) {
            doOptimize(order);

            if (best == null || order.getGain() > best.getGain()) {
                best = order;
            }
        }
        return best;
    }

    private void doOptimize(TripOrderEdge order) {
        if (order.getPredecessors().isEmpty()) {
            order.setGain(order.getOrder().getCost());
            return;
        }

        for (TripOrderEdge predecessor : order.getPredecessors()) {
            long potentialGain = predecessor.getGain() + order.getOrder().getCost();
            if (potentialGain > order.getGain()) {
                order.setGain(potentialGain);
                order.setPredecessor(predecessor);
            }
        }
    }
}
