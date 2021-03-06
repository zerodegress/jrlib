package cn.rwtools.jrlib;

public final class Util {

    public static final String lineSeperator = System.getProperty("line.separator");

    public static boolean isValidFilename(String filename) {
        if (filename == null || filename.length() > 255) {
            return false;
        }
        else if (filename.matches(
                ("[^\\s\\\\/:\\*\\?\\\"<>\\|](\\x20|[^\\s\\\\/:\\*\\?\\\"<>\\|])*[^\\s\\\\/:\\*\\?\\\"<>\\|\\.]$"))) {
            return true;
        }
        else {
            return false;
        }
    }
}
