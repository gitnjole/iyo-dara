package iyo.dara.transaction.service;

import iyo.dara.account.domain.AccountType;
import iyo.dara.core.sheets.SheetsTransformer;
import iyo.dara.core.util.ParsingUtils;
import iyo.dara.transaction.domain.Category;
import iyo.dara.transaction.domain.Subcategory;
import iyo.dara.transaction.domain.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("transactionTransformer")
public class TransactionTransformer implements SheetsTransformer<Transaction> {
    @Override
    public Transaction fromSheet(List<Object> row) {
        return new Transaction(
                ParsingUtils.getString(row, 0),
                Category.valueOf(ParsingUtils.getString(row, 1)),
                parseSubcategory(ParsingUtils.getString(row, 2)),
                ParsingUtils.parseCost(ParsingUtils.getString(row, 2)),
                ParsingUtils.parseDate(ParsingUtils.getString(row, 3)),
                AccountType.valueOf(ParsingUtils.getString(row, 5)),
                Boolean.parseBoolean(ParsingUtils.getString(row, 6))
        );
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
