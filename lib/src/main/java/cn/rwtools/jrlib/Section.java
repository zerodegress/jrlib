package cn.rwtools.jrlib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

public class Section {
    private String name;
    private String note;
    private Map<String, PropertyValue> properties;

    public Section(String name) {
        this.name = name;
        note = "";
        properties = new LinkedHashMap<>();
    }

    public Section(String name, int pos) {
        this.name = name;
        note = "";
        properties = new LinkedHashMap<>();
    }

    public PropertyValue get(String key) {
        if (properties.containsKey(key)) {
            return properties.get(key);
        }
        else {
            return null;
        }
    }
    
    public String getAsString(String key) {
        if (properties.containsKey(key)) {
            return properties.get(key).asString();
        }
        else {
            return null;
        }
    }

    public void add(String key, String value) {
        properties.put(key, new PropertyValue(value));
    }

    public void remove(String key) {
        properties.remove(key);
    }

    public Entry<String, PropertyValue> find(Predicate<String> pattern) {
        for (Entry<String, PropertyValue> entry : properties.entrySet()) {
            if (pattern.test(entry.getKey())) {
                return entry;
            }
        }
        return null;
    }

    public Collection<Entry<String, PropertyValue>> filter(Predicate<String> pattern) {
        ArrayList<Entry<String, PropertyValue>> results = new ArrayList<>();
        for (Entry<String, PropertyValue> entry : properties.entrySet()) {
            if (pattern.test(entry.getKey())) {
                results.add(entry);
            }
        }
        return results;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public String getName() {
        return name;
    }

    public String toSectionText() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("[%s]", name)).append(Util.lineSeperator);
        if (note != "") {
            sb.append(note).append(Util.lineSeperator);
        }
        for (Entry<String, PropertyValue> entry : properties.entrySet()) {
            sb.append(String.format("%s:%s", entry.getKey(), entry.getValue())).append(Util.lineSeperator);
        }
        return sb.toString().strip();
    }
}
