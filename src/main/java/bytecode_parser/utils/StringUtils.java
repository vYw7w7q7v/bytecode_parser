package bytecode_parser.utils;

import java.text.MessageFormat;
import java.util.List;

public class StringUtils {
    private StringUtils() {
    }

    public static String addPrefixForEachLine(String s, String prefix) {
        StringBuilder builder = new StringBuilder();
        List<String> lines = s.lines().toList();
        int l = lines.size();
        for (int i = 0; i < l; i++) {
            String line = lines.get(i);
            builder.append(MessageFormat.format("{0}{1}{2}", prefix, line, i == l - 1 ? "" : "\n"));
        }
        return builder.toString();
    }
}
