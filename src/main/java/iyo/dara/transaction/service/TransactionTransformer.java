package iyo.dara.transaction.service;

import iyo.dara.account.domain.AccountType;
import iyo.dara.core.sheets.SheetsTransformer;
import iyo.dara.transaction.domain.Category;
import iyo.dara.transaction.domain.Subcategory;
import iyo.dara.transaction.domain.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Component("transactionTransformer")
public class TransactionTransformer implements SheetsTransformer<Transaction> {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("d.M.yyyy.");

    @Override
    public Transaction fromSheet(List<Object> row) {
        return new Transaction(
                getString(row, 0),
                Category.valueOf(getString(row, 1)),
                parseSubcategory(getString(row, 2)),
                parseCost(getString(row, 3)),
                parseDate(getString(row, 4)),
                AccountType.valueOf(getString(row, 5)),
                Boolean.parseBoolean(getString(row, 6))
        );
    }

    private String getString(List<Object> row, int index) {
        return index < row.size() ? row.get(index).toString() : "";
    }

    private double parseCost(String raw) {
        if (raw == null || raw.isBlank()) return 0.0;
        String cleaned = raw.replaceAll("[^0-9,.-]", "").replace(",", ".");
        try {
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private LocalDate parseDate(String raw) {
        if (raw == null || raw.isBlank()) return LocalDate.now();
        try {
            return LocalDate.parse(raw, DATE_FORMAT);
        } catch (DateTimeParseException e) {
            return LocalDate.now();
        }
    }

    private Subcategory parseSubcategory(String value) {
        if (value == null || value.trim().isEmpty()) return Subcategory.None;
        try {
            return Subcategory.valueOf(value);
        } catch (IllegalArgumentException e) {
            return Subcategory.None;
        }
    }
}
