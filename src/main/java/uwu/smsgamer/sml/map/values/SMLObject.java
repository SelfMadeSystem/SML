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

    public SMLValue set(String s, SMLValue value) {
        String[] split = s.split(":");
        String last = split[split.length - 1];
        SMLObject object = this;
        for (int i = 0; i < split.length - 1; i++) {
            String name = split[i];
            SMLValue v = object.get(name);
            if (v == null) {
                object = (SMLObject) object.add(new SMLNode(name, new SMLObject()));
            } else if (v instanceof SMLObject) {
                object = (SMLObject) v;
            } else throw new IllegalArgumentException("Not an SMLObject: " + name);
        }
        SMLNode node = object.values.get(last);
        if (node == null) object.values.put(last, new SMLNode(last, value));
        else node.value = value;
        return value;
    }

    public SMLValue add(SMLNode value) {
        this.values.put(value.name, value);
        return value.value;
    }

    public SMLValue get(String s) {
        String[] split = s.split(":");
        SMLObject object = this;
        for (int i = 0; i < split.length - 1; i++) {
            String name = split[i];
            SMLValue v = object.get(name);
            if (v == null) {
                return null;
            } else if (v instanceof SMLObject) {
                object = (SMLObject) v;
            } else throw new IllegalArgumentException("Not an SMLObject: " + name);
        }
        SMLNode node = object.values.get(split[split.length - 1]);
        return node == null ? null : node.value;
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
