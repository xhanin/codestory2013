package cs13.jjrental;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
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
        Preconditions.checkNotNull(tripOrders);
        if (tripOrders.size() <= 1) {
            return new JJOptimization(Lists.newArrayList(tripOrders));
        }

        List<TripOrder> sortedOrders = Lists.newArrayList(tripOrders);
        Collections.sort(sortedOrders);
        List<TripOrderEdge> edges = Lists.newArrayListWithCapacity(tripOrders.size());
        for (TripOrder order : sortedOrders) {
            edges.add(new TripOrderEdge(order));
        }

        TripOrderEdge best = doOptimize(ImmutableList.copyOf(edges));

        return new JJOptimization(buildPath(best));
    }

    private List<TripOrder> buildPath(TripOrderEdge edge) {
        List<TripOrder> path = Lists.newLinkedList();

        for (TripOrderEdge current = edge; current != null; current = current.getPredecessor()) {
            path.add(0, current.getOrder());
        }

        return path;
    }

    private TripOrderEdge doOptimize(ImmutableList<TripOrderEdge> orders) {
        TripOrderEdge best = null;
        for (int i = 0; i < orders.size(); i++) {
            TripOrderEdge order = orders.get(i);
            doOptimize(order, orders.subList(0, i));

            if (best == null || order.getGain() > best.getGain()) {
                best = order;
            }
        }
        return best;
    }

    private void doOptimize(TripOrderEdge order, List<TripOrderEdge> edges) {
        order.setGain(order.getOrder().getCost());
        for (int i = edges.size() - 1; i >= 0; i--) {
            TripOrderEdge edge = edges.get(i);
            if (edge.getOrder().isCompatibleWith(order.getOrder())) {
                long potentialGain = edge.getGain() + order.getOrder().getCost();
                if (potentialGain > order.getGain()) {
                    order.setGain(potentialGain);
                    order.setPredecessor(edge);
                }
            }
        }
    }
}
