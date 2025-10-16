package iyo.dara.core.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class ParsingUtils {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("d.M.yyyy.");

    private ParsingUtils() {}

    public static String getString(java.util.List<Object> row, int index) {
        return index < row.size() ? row.get(index).toString() : "";
    }

    public static double parseCost(String raw) {
        if (raw == null || raw.isBlank()) return 0.0;

        String cleaned = raw.replaceAll("[^0-9,]", "");

        try {
            return NumberFormat.getInstance(Locale.GERMANY).parse(cleaned).doubleValue();
        } catch (ParseException e) {
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

    public static String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("d.M.yyyy."));
    }

    public static String formatCost(double cost) {
        return String.valueOf(cost);
    }

    public static String formatCostForSheets(double cost) {
        return String.format(Locale.GERMANY, "%.2f", cost);
    }

    public static <E extends Enum<E>> E parseEnum(String value, Class<E> enumType) {
        return Enum.valueOf(enumType, value);
    }
}
