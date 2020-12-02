package uwu.smsgamer.sml.map.values;

public class SMLBool extends SMLValue {
    public static final SMLBool TRUE = new SMLBool(true);
    public static final SMLBool FALSE = new SMLBool(false);

    public boolean value;

    public SMLBool(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value ? "true" : "false";
    }

    @Override
    public String beginString() {
        return "(";
    }

    @Override
    public String endString() {
        return ")";
    }
}
