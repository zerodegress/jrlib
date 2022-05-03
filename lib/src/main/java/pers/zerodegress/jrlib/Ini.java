package pers.zerodegress.jrlib;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Ini {
    private String filename;
    private String note;
    private int lastSectionPos;
    private Map<String, Section> sections;

    public Ini() {
        filename = "untitled.ini";
        note = "";
        sections = new TreeMap<>();
        lastSectionPos = 0;
    }

    public Ini(String filename) {
        if (Util.isValidFilename(filename)) {
            throw new CodeException("文件名不合法");
        }
        this.filename = filename;
        note = "";
        sections = new TreeMap<>();
        lastSectionPos = 0;
    }

    public static Ini parse(String src) {
        Ini ini = new Ini();
        Section ptr = null;
        String[] lines = src.split("\\r?\\n");
        Iterator<String> linesIter = Arrays.asList(lines).iterator();
        while (linesIter.hasNext()) {
            String line = linesIter.next().strip();
            if (line.isBlank()) {
                continue;
            }
            else if (line.startsWith("#")) {
                if (ptr == null) {
                    ini.setNote(ini.getNote() + line + Util.lineSeperator);
                } else {
                    ptr.setNote(ptr.getNote() + line + Util.lineSeperator);
                }
            }
            else if (line.matches("\\[.+\\]")) {
                if (ptr != null) {
                    ptr.setNote(ptr.getNote().strip());
                }
                String sectionName = line.replace("[", "").replace("]", "");
                ini.add(sectionName);
                ptr = ini.get(sectionName);
            }
            else if (line.contains(":")) {
                if (ptr == null) {
                    throw new CodeException("ini文本头部出现属性");
                }
                String key = line.split(":", 2)[0].strip();
                String value = line.split(":", 2)[1].strip();
                if (value.endsWith("\"\"\"")) {
                    StringBuilder vb = new StringBuilder(value);
                    while (true) {
                        if (!linesIter.hasNext()) {
                            throw new CodeException("ini文本三引号不封闭");
                        }
                        vb.append(linesIter.next());
                        if (vb.toString().strip().endsWith("\"\"\"")) {
                            value = vb.toString();
                            break;
                        }
                    }
                }
                ptr.add(key, value);
            }
            else {
                throw new CodeException("ini文本出现未定义元素");
            }
        }
        return ini;
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
