package cs13.jjrental;

import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * User: xavierhanin
 * Date: 1/12/13
 * Time: 1:44 PM
 */
public class JJRentalOptimizerTest {
    private JJRentalOptimizer optimizer = new JJRentalOptimizer();

    @Test
    public void should_example_return_best_path() {
        TripOrder monad, meta, legacy, yagni;
        JJOptimization optimization = optimizer.optimize(asList(
                monad = new TripOrder("MONAD42", 0, 5, 10),
                meta = new TripOrder("META18", 3, 7, 14),
                legacy = new TripOrder("LEGACY01", 5, 9, 8),
                yagni = new TripOrder("YAGNI17", 5, 9, 7)
        ));

        assertThat(optimization, is(notNullValue(JJOptimization.class)));
        assertThat(optimization.getGain(), is(18L));
        assertThat(optimization.getOrdersPath(), is(asList(monad, legacy)));
    }
}
