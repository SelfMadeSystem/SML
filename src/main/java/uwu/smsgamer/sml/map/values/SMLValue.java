package uwu.smsgamer.sml.map.values;

public abstract class SMLValue {
    public abstract String toString();
    public abstract String beginString();
    public abstract String endString();

    public String completeString() {
        return beginString() + toString() + endString();
    }
}
