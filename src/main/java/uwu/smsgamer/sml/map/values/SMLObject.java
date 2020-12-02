package uwu.smsgamer.sml.map.values;

import uwu.smsgamer.sml.map.SMLNode;

import java.util.LinkedHashMap;

public class SMLObject extends SMLIndentable {
    public LinkedHashMap<String, SMLNode> values;

    public SMLObject() {
        this(new LinkedHashMap<>());
    }

    public SMLObject(LinkedHashMap<String, SMLNode> values) {
        this.values = values;
    }

    public SMLValue add(SMLNode value) {
        this.values.put(value.name, value);
        return value.value;
    }

    public SMLValue get(String s) {
        String[] split = s.split(":");
        SMLObject object = this;
        for (int i = 0; i < split.length - 1; i++) {
            String s1 = split[i];
            SMLValue v = object.get(s1);
            if (v instanceof SMLObject) {
                object = (SMLObject) v;
            } else throw new IllegalArgumentException("Not an SMLObject: " + s1);
        }
        return object.values.get(split[split.length - 1]).value;
    }

    @Override
    public String toString() {
        return toString(0);
    }

    public String toString(int indent) {
        StringBuilder s = new StringBuilder(beginString()).append("\n");
        for (SMLNode value : values.values()) s.append(value.toString(indent + 1)).append("\n");
        while (--indent > 0) s.append(" ");
        return s.append(endString()).toString();
    }

    @Override
    public String beginString() {
        return "{";
    }

    @Override
    public String endString() {
        return "}";
    }
}
