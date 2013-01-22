package cs13.jjrental;

import com.google.common.base.Preconditions;
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

    private TripOrderEdge doOptimize(List<TripOrderEdge> edges) {
        TripOrderEdge best = null;
        TripOrderEdge[] bestsByEndHour = new TripOrderEdge[edges.get(edges.size() - 1).getOrder().getEndHour() + 1];
        for (TripOrderEdge edge : edges) {
            TripOrder order = edge.getOrder();
            for (int i = order.getStartHour(); i>=0 && edge.getGain() == -1; i--) {
                if (bestsByEndHour[i] != null) {
                    edge.setPredecessor(bestsByEndHour[i]);
                    edge.setGain(order.getCost() + edge.getPredecessor().getGain());
                }
            }

            if (edge.getGain() == -1) {
                edge.setGain(order.getCost());
            }

            if (best == null || edge.getGain() > best.getGain()) {
                best = edge;
                bestsByEndHour[order.getEndHour()] = edge;
            }
        }
        return best;
    }
}
