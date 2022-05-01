package pers.zerodegress.jrlib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.function.Predicate;

public class Section {
    private String name;
    private String note;
    private int pos;
    private Hashtable<String, String> properties;

    public Section(String name) {
        this.name = name;
        note = "";
        pos = 0;
        properties = new Hashtable<>();
    }

    public Section(String name,int pos){
        this.name = name;
        note = "";
        this.pos = pos;
        properties = new Hashtable<>();
    }

    public void add(String key, String value) {
        properties.put(key, value);
    }

    public void remove(String key) {
        properties.remove(key);
    }

    public Entry<String, String> find(Predicate<String> pattern) {
        for (Entry<String, String> entry : properties.entrySet()) {
            if (pattern.test(entry.getKey())) {
                return entry;
            }
        }
        return null;
    }

    public Collection<Entry<String, String>> filter(Predicate<String> pattern) {
        ArrayList<Entry<String, String>> results = new ArrayList<>();
        for (Entry<String, String> entry : properties.entrySet()) {
            if (pattern.test(entry.getKey())) {
                results.add(entry);
            }
        }
        return results;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public int getPos() {
        return pos;
    }

    public String getName() {
        return name;
    }
}
