package uwu.smsgamer.sml.map.values;

public abstract class SMLIndentable extends SMLValue {
    public String toString() {
        return toString(0);
    }

    public abstract String toString(int indent);
}
