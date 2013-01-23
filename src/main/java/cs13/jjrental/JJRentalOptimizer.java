package cs13.jjrental;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
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
            return new JJOptimization(
                    tripOrders.isEmpty() ? 0 : Iterables.getFirst(tripOrders, null).getCost(),
                    Lists.newArrayList(tripOrders));
        }

        List<TripOrder> sortedOrders = Lists.newArrayList(tripOrders);
        Collections.sort(sortedOrders);

        TripOrderEdge best = doOptimize(sortedOrders);

        return new JJOptimization(best.getGain(), best.buildPath());
    }

    private TripOrderEdge doOptimize(List<TripOrder> edges) {
        TripOrderEdge best = null;
        TripOrderEdge[] bestsByEndHour = new TripOrderEdge[edges.get(edges.size() - 1).getEndHour() + 1];
        for (TripOrder order : edges) {
            long gain = -1;
            TripOrderEdge predecessor = null;
            for (int i = order.getStartHour(); i>=0 && gain == -1; i--) {
                if (bestsByEndHour[i] != null) {
                    predecessor = bestsByEndHour[i];
                    gain = order.getCost() + predecessor.getGain();
                }
            }

            if (gain == -1) {
                gain = order.getCost();
            }

            if (best == null || gain > best.getGain()) {
                bestsByEndHour[order.getEndHour()] = best = new TripOrderEdge(order, Optional.fromNullable(predecessor), gain);
            }
        }
        return best;
    }
}
