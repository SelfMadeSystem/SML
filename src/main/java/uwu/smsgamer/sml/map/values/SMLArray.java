package uwu.smsgamer.sml.map.values;

import java.util.*;

public class SMLArray extends SMLIndentable {
    public ArrayList<SMLValue> values;

    public SMLArray() {
        this(new ArrayList<>());
    }

    public SMLArray(ArrayList<SMLValue> values) {
        this.values = values;
    }

    public SMLArray(SMLValue... values) {
        this(new ArrayList<>(Arrays.asList(values)));
    }

    public void add(SMLValue value) {
        values.add(value);
    }

    @Override
    public String toString(int indent) {
        StringBuilder s = new StringBuilder();
        s.append(beginString()).append("\n");
        for (SMLValue value : values) {
            for (int i = indent; i-- > 0; ) s.append(" ");
            if (value instanceof SMLIndentable) s.append(((SMLIndentable) value).toString(indent + 1)).append("\n");
            else s.append(value.completeString()).append("\n");
        }
        while (--indent > 0) s.append(" ");
        return s.append(endString()).toString();
    }

    @Override
    public String beginString() {
        return "[";
    }

    @Override
    public String endString() {
        return "]";
    }
}
