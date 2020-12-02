package uwu.smsgamer.sml.map.values;

public class SMLNull extends SMLValue {
    private final static SMLNull instance = new SMLNull();

    public static SMLNull getInstance() {
        return instance;
    }

    private SMLNull() {}

    @Override
    public String toString() {
        return "";
    }

    @Override
    public String beginString() {
        return "";
    }

    @Override
    public String endString() {
        return "";
    }
}
