package uwu.smsgamer.sml.map.values;

public class SMLNum extends SMLValue {
    public double value;

    public SMLNum(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        String s = Double.toString(value);
        if (s.endsWith(".0")) return s.substring(0, s.length() - 2);
        return s;
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
