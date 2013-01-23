package cs13.jjrental;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * User: xavierhanin
 * Date: 1/12/13
 * Time: 1:53 PM
 */
public class JJOptimization {
    private final long gain;
    @JsonIgnore
    private final List<TripOrder> ordersPath;

    public JJOptimization(long gain, List<TripOrder> ordersPath) {
        this.gain = gain;
        this.ordersPath = ordersPath;
    }

    public long getGain() {
        return gain;
    }

    public List<TripOrder> getOrdersPath() {
        return ordersPath;
    }

    @JsonProperty
    public List<String> getPath() {
        return Lists.transform(ordersPath, new Function<TripOrder, String>() {
            @Override
            public String apply(TripOrder input) {
                return input.getName();
            }
        });
    }

    @Override
    public String toString() {
        return "JJOptimization{" +
                "gain=" + gain +
                ", ordersPath=" + ordersPath +
                '}';
    }

}
