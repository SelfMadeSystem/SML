package uwu.smsgamer.sml.map;

import uwu.smsgamer.sml.map.values.*;

public class SMLNode {
    public String name;
    public SMLValue value;

    public SMLNode() {
    }

    public SMLNode(String name) {
        this.name = name;
        this.value = new SMLObject();
    }

    public SMLNode(SMLValue value) {
        this.value = value;
    }

    public SMLNode(String name, SMLValue value) {
        this.name = name;
        this.value = value;
    }

    public String toString() {
        return toString(0);
    }

    public String toString(int indent) {
        StringBuilder s = new StringBuilder();
        for (int i = indent; i-- > 0;) s.append(" ");
        s.append(":").append(name);
        if (value instanceof SMLIndentable) s.append(((SMLIndentable) value).toString(indent + 1));
        else s.append(value.completeString());
        return s.append(":").toString();
    }
}
