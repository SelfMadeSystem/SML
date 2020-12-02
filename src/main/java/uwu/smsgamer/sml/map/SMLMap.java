package uwu.smsgamer.sml.map;

import uwu.smsgamer.sml.map.values.*;

public class SMLMap {
    public ParentNode parentNode = new ParentNode();

    public String toString() {
        return parentNode.toString(1);
    }

    public SMLValue getValue(String s) {
        return parentNode.value.get(s);
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
