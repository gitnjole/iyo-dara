package iyo.dara.menza.service;

import iyo.dara.account.domain.AccountType;
import iyo.dara.core.sheets.SheetsTransformer;
import iyo.dara.core.util.ParsingUtils;
import iyo.dara.menza.domain.Menza;
import iyo.dara.menza.domain.Store;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("menzaTransformer")
public class MenzaTransformer implements SheetsTransformer<Menza> {
    @Override
    public Menza fromSheet(List<Object> row) {
        return new Menza(
                parseStore(ParsingUtils.getString(row, 0)),
                ParsingUtils.parseCost(ParsingUtils.getString(row, 1)),
                ParsingUtils.parseDate(ParsingUtils.getString(row, 2)),
                AccountType.valueOf(ParsingUtils.getString(row, 3))
        );
    }

    private Store parseStore(String value) {
        if (value == null || value.isBlank()) return null;
        String normalized = value.toUpperCase().replaceAll("[^A-Z]", "_");
        try {
            return Store.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
