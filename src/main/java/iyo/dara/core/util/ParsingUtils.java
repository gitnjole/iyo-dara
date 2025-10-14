package iyo.dara.core.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ParsingUtils {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("d.M.yyyy.");

    private ParsingUtils() {}

    public static String getString(java.util.List<Object> row, int index) {
        return index < row.size() ? row.get(index).toString() : "";
    }

    public static double parseCost(String raw) {
        if (raw == null || raw.isBlank()) return 0.0;

        String cleaned = raw.replaceAll("[^0-9,.-]", "");

        if (cleaned.matches(".*\\.[0-9]{3,}.*") && cleaned.contains(",")) {
            cleaned = cleaned.replace(".", "").replace(",", ".");
        } else if (cleaned.chars().filter(ch -> ch == ',').count() == 1
                && cleaned.indexOf(',') > cleaned.length() - 3) {
            cleaned = cleaned.replace(",", ".");
        } else {
            cleaned = cleaned.replace(",", "");
        }

        try {
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public static LocalDate parseDate(String raw) {
        if (raw == null || raw.isBlank()) return LocalDate.now();
        try {
            return LocalDate.parse(raw, DATE_FORMAT);
        } catch (DateTimeParseException e) {
            return LocalDate.now();
        }
    }
}
