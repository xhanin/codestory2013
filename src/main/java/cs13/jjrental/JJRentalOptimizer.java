package cs13.jjrental;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.util.*;

/**
 * User: xavierhanin
 * Date: 1/12/13
 * Time: 1:52 PM
 */
public class JJRentalOptimizer {
    public JJOptimization optimize(Collection<TripOrder> tripOrders) {
        if (tripOrders.size() == 1) {
            return new JJOptimization(Lists.newArrayList(tripOrders));
        }

        List<TripOrder> orders = Lists.newArrayList(tripOrders);
        Collections.sort(orders);

        JJOptimization optimization = new JJOptimization(Collections.<TripOrder>emptyList());
        Set<TripOrder> testedOrders = new HashSet<>();
        for (int i = 0; i < orders.size(); i++) {
            final TripOrder tripOrder = orders.get(i);

            if (testedOrders.contains(tripOrder)) {
                // it has already been tested after a previous order, it can't provide a better cost
                continue;
            }

            Collection<TripOrder> otherOrders = Collections2.filter(orders, new Predicate<TripOrder>() {
                @Override
                public boolean apply(TripOrder input) {
                    return input != null && tripOrder.isCompatibleWith(input);
                }
            });

            JJOptimization newOptimization = new JJOptimization(Lists.asList(
                    tripOrder, optimize(otherOrders).getOrdersPath().toArray(new TripOrder[0])));

            if (newOptimization.compareTo(optimization) > 0) {
                optimization = newOptimization;
            }

            testedOrders.addAll(otherOrders);
        }

        return optimization;
    }
}
