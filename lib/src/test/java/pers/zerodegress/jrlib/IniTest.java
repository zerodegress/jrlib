package pers.zerodegress.jrlib;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IniTest {
    @Test
    void add() {
        Ini ini = new Ini();
        ini.add("newSection");
        assertEquals(ini.get("newSection") != null, true);
        ini.get("newSection").add("test", "123");
        assertEquals(ini.toIniText(), String.format("[newSection]%stest:123", Util.lineSeperator));
    }

    @Test
    void parse() {
        Ini ini = new Ini();
        ini.add("newSection");
        ini.get("newSection").add("test", "123");
        Ini nIni = Ini.parse(ini.toIniText());
        assertEquals(nIni.get("newSection") != null, true);
        assertEquals(nIni.get("newSection").get("test"), "123");
    }
}
