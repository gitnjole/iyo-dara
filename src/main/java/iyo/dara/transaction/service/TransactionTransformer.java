package iyo.dara.transaction.service;

import iyo.dara.account.domain.AccountType;
import iyo.dara.core.sheets.IOSheetsTransformer;
import iyo.dara.transaction.domain.Category;
import iyo.dara.transaction.domain.Subcategory;
import iyo.dara.transaction.domain.Transaction;
import iyo.dara.transaction.write.TransactionWrite;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static iyo.dara.core.util.ParsingUtils.*;

@Component("transactionTransformer")
public class TransactionTransformer implements IOSheetsTransformer<Transaction, TransactionWrite.TransactionDto> {

    @Override
    public Transaction fromSheet(List<Object> row) {
        return new Transaction(
                getString(row, 0),
                parseEnum(getString(row, 1), Category.class),
                parseEnum(getString(row, 2), Subcategory.class),
                parseCost(getString(row, 3)),
                parseDate(getString(row, 4)),
                parseEnum(getString(row, 5), AccountType.class),
                Boolean.parseBoolean(getString(row, 6))
        );
    }

    @Override
    public List<Object> toSheet(Transaction t) {
        return List.of(
                t.detail(),
                t.category().name(),
                t.subcategory().name(),
                formatCostForSheets(t.cost()),
                formatDate(t.date()),
                t.account().name(),
                t.impulse()
        );
    }

    @Override
    public Transaction fromDto(TransactionWrite.TransactionDto dto) {
        return new Transaction(
                dto.detail(),
                Category.valueOf(dto.category()),
                Subcategory.valueOf(dto.subcategory()),
                dto.cost(),
                LocalDate.parse(dto.date()),
                AccountType.valueOf(dto.paymentSource()),
                dto.impulse()
        );
    }
}