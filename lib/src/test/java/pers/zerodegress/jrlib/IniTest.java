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
        System.out.println(ini.toIniText());
    }
}
