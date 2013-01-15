package cs13.jjrental;

import java.util.List;

/**
 * User: xavierhanin
 * Date: 1/15/13
 * Time: 4:44 PM
 */
public class TripOrderEdge implements Comparable<TripOrderEdge> {
    private final TripOrder order;
    private final List<TripOrderEdge> predecessors;

    private long gain;
    private TripOrderEdge predecessor;

    public TripOrderEdge(TripOrder order, List<TripOrderEdge> predecessors) {
        this.order = order;
        this.predecessors = predecessors;
    }

    public TripOrder getOrder() {
        return order;
    }

    public List<TripOrderEdge> getPredecessors() {
        return predecessors;
    }

    public long getGain() {
        return gain;
    }

    public void setGain(long gain) {
        this.gain = gain;
    }

    public TripOrderEdge getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(TripOrderEdge predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public int compareTo(TripOrderEdge o) {
        return order.compareTo(o.getOrder());
    }
}
