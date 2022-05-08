package cn.rwtools.jrlib;

public class PropertyValue {
    private String value;

    public PropertyValue(String value) {
        this.value = value.strip();
    }

    public String asString() {
        return value;
    }

    public String asMultiLine() {
        return String.format("\"\"\"%s\"\"\"", value);
    }

    public String[] asList() {
        return value.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
    }

    public int asInt() {
        return Integer.parseInt(value);
    }

    public float asFloat() {
        return Float.parseFloat(value);
    }

    void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return asString();
    }
}
