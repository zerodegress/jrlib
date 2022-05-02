package pers.zerodegress.jrlib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.function.Predicate;

public class Ini {
    private String filename;
    private String note;
    private int lastSectionPos;
    private Hashtable<String, Section> sections;

    public Ini() {
        filename = "untitled.ini";
        note = "";
        sections = new Hashtable<>();
        lastSectionPos = 0;
    }

    public Ini(String filename) {
        if (Util.isValidFilename(filename)) {
            throw new CodeException("文件名不合法");
        }
        this.filename = filename;
        note = "";
        sections = new Hashtable<>();
        lastSectionPos = 0;
    }

    public void add(String sectionName) {
        sections.put(sectionName, new Section(sectionName, lastSectionPos + 1));
        lastSectionPos++;
    }
    
    public void remove(String sectionName) {
        sections.remove(sectionName);
    }

    public Section get(String sectionName) {
        return sections.get(sectionName);
    }

    public boolean contains(String sectionName) {
        return sections.containsKey(sectionName);
    }

    public Section find(Predicate<Section> pattern) {
        for (Section section : sections.values()) {
            if (pattern.test(section)) {
                return section;
            }
        }
        return null;
    }

    public Collection<Section> filter(Predicate<Section> pattern) {
        ArrayList<Section> results = new ArrayList<>();
        for (Section section : sections.values()) {
            if (pattern.test(section)) {
                results.add(section);
            }
        }
        return results;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getFilename() {
        return filename;
    }

    public String getNote() {
        return note;
    }

    public Collection<Section> getSections() {
        return sections.values();
    }

    public String toIniText() {
        StringBuilder sb = new StringBuilder();
        sb.append(note).append(Util.lineSeperator);
        for (Section section : sections.values()) {
            sb.append(section.toSectionText()).append(Util.lineSeperator);
        }
        return sb.toString().strip();
    }
}
