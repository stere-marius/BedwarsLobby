package ro.marius.bedwars.utils;

public class StringUtils {

    public static String getFirstLetterUpperCase(String string) {

        if ((string == null) || string.isEmpty()) {
            return "";
        }

        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

}
