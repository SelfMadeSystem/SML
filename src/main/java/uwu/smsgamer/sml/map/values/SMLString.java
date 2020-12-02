package uwu.smsgamer.sml.map.values;

public class SMLString extends SMLValue {
    public String value;
    public String completedString;

    public SMLString(String value) {
        this.value = value;
        this.completedString = getFormattedString(value);
    }

    public SMLString(String value, String completedString) {
        this.value = value;
        this.completedString = completedString;
    }

    public void setValue(String value) {
        this.value = value;
        this.completedString = getFormattedString(value);
    }

    @Override
    public String completeString() {
        return beginString() + completedString + endString();
    }

    @Override
    public String toString() {
        return value.trim();
    }

    @Override
    public String beginString() {
        return "\"";
    }

    @Override
    public String endString() {
        return "\"";
    }

    public static String getFormattedString(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
