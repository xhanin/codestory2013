package cs13.jjrental;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * User: xavierhanin
 * Date: 1/12/13
 * Time: 1:53 PM
 */
public final class TripOrder implements Comparable<TripOrder> {
    @JsonProperty("VOL")
    private String name;
    @JsonProperty("DEPART")
    private int startHour;
    @JsonProperty("DUREE")
    private int duration;
    @JsonProperty("PRIX")
    private int cost;

    public TripOrder() {
    }

    public TripOrder(String name, int startHour, int duration, int cost) {
        if (name == null) {
            throw new IllegalArgumentException();
        }

        this.name = name;
        this.startHour = startHour;
        this.duration = duration;
        this.cost = cost;
    }

    public boolean isCompatibleWith(TripOrder order) {
        // we compare only to later orders
        return order.getStartHour() >= getStartHour() + getDuration();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getDuration() {
        return duration;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "TripOrder{" +
                "name='" + name + '\'' +
                ", startHour=" + startHour +
                ", duration=" + duration +
                ", cost=" + cost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TripOrder tripOrder = (TripOrder) o;

        if (cost != tripOrder.cost) return false;
        if (duration != tripOrder.duration) return false;
        if (startHour != tripOrder.startHour) return false;
        if (!name.equals(tripOrder.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + startHour;
        result = 31 * result + duration;
        result = 31 * result + cost;
        return result;
    }

    @Override
    public int compareTo(TripOrder o) {
        return Integer.compare(getStartHour(), o.getStartHour());
    }
}
