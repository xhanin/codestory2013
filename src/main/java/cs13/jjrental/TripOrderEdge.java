package cs13.jjrental;

/**
 * User: xavierhanin
 * Date: 1/15/13
 * Time: 4:44 PM
 */
public final class TripOrderEdge implements Comparable<TripOrderEdge> {
    private final TripOrder order;

    private long gain = -1;
    private TripOrderEdge predecessor;

    public TripOrderEdge(TripOrder order) {
        this.order = order;
    }

    public TripOrder getOrder() {
        return order;
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
