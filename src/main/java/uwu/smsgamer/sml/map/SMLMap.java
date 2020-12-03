package uwu.smsgamer.sml.map;

import uwu.smsgamer.sml.map.values.*;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class SMLMap {
    public ParentNode parentNode = new ParentNode();

    public String toString() {
        return parentNode.toString(1);
    }

    public SMLValue getValue(String s) {
        return parentNode.value.get(s);
    }

    public boolean getBoolean(String s) {
        SMLValue v = getValue(s);
        if (v instanceof SMLBool) return ((SMLBool) v).value;
        return false;
    }

    public byte getByte(String s) {
        SMLValue v = getValue(s);
        if (v instanceof SMLNum) return (byte) ((SMLNum) v).value;
        return 0;
    }

    public short getShort(String s) {
        SMLValue v = getValue(s);
        if (v instanceof SMLNum) return (short) ((SMLNum) v).value;
        return 0;
    }

    public int getInt(String s) {
        SMLValue v = getValue(s);
        if (v instanceof SMLNum) return (int) ((SMLNum) v).value;
        return 0;
    }

    public long getLong(String s) {
        SMLValue v = getValue(s);
        if (v instanceof SMLNum) return (long) ((SMLNum) v).value;
        return 0;
    }

    public float getFloat(String s) {
        SMLValue v = getValue(s);
        if (v instanceof SMLNum) return (float) ((SMLNum) v).value;
        return 0;
    }

    public double getDouble(String s) {
        SMLValue v = getValue(s);
        if (v instanceof SMLNum) return ((SMLNum) v).value;
        return 0;
    }

    public String getString(String s) {
        SMLValue v = getValue(s);
        if (v instanceof SMLString) return ((SMLString) v).value;
        return null;
    }

    public List<SMLValue> getList(String s) {
        SMLValue v = getValue(s);
        if (v instanceof SMLArray) return ((SMLArray) v).values;
        return null;
    }

    public List<String> getStringList(String s) {
        List<SMLValue> list = getList(s);
        if (list == null) return null;
        return list.stream().map(SMLValue::toString).collect(Collectors.toList());
    }

    public List<Double> getDoubleList(String s) {
        List<SMLValue> list = getList(s);
        if (list == null) return null;
        return list.stream().map(v -> v instanceof SMLNum ? ((SMLNum) v).value : 0D).collect(Collectors.toList());
    }

    public List<Boolean> getBoolList(String s) {
        List<SMLValue> list = getList(s);
        if (list == null) return null;
        return list.stream().map(v -> v instanceof SMLBool && ((SMLBool) v).value).collect(Collectors.toList());
    }

    public SMLValue set(String s, Object obj) {
        if (obj instanceof SMLValue)
            return parentNode.value.set(s, (SMLValue) obj);
        if (obj instanceof Boolean)
            return parentNode.value.set(s, new SMLBool((Boolean) obj));
        if (obj instanceof Number)
            return parentNode.value.set(s, new SMLNum(((Number) obj).doubleValue()));
        if (obj instanceof String)
            return parentNode.value.set(s, new SMLString((String) obj));
        else throw new IllegalArgumentException("Unknown value: " + obj);
    }

    public static class ParentNode extends SMLNode {
        public SMLObject value;

        public ParentNode() {
            this.value = new SMLObject();
        }

        @Override
        public String toString(int indent) {
            return value.toString(indent);
        }
    }
}
