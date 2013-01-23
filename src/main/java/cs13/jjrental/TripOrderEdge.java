package cs13.jjrental;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * User: xavierhanin
 * Date: 1/15/13
 * Time: 4:44 PM
 */
public final class TripOrderEdge {
    private final TripOrder order;

    private final long gain;
    private final Optional<TripOrderEdge> predecessor;

    public TripOrderEdge(TripOrder order, Optional<TripOrderEdge> predecessor, long gain) {
        this.order = order;
        this.gain = gain;
        this.predecessor = predecessor;
    }

    public TripOrder getOrder() {
        return order;
    }

    public long getGain() {
        return gain;
    }

    public Optional<TripOrderEdge> getPredecessor() {
        return predecessor;
    }

    public List<TripOrder> buildPath() {
        List<TripOrder> path = Lists.newLinkedList();

        for (Optional<TripOrderEdge> current = Optional.of(this); current.isPresent(); current = current.get().getPredecessor()) {
            path.add(0, current.get().getOrder());
        }

        return path;
    }

}
