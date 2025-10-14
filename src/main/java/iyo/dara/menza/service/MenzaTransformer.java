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
                //ParsingUtils.getString(row,0),
                Store.valueOf(ParsingUtils.getString(row, 1)),
                ParsingUtils.parseCost(ParsingUtils.getString(row, 2)),
                ParsingUtils.parseDate(ParsingUtils.getString(row, 3)),
                AccountType.valueOf(ParsingUtils.getString(row, 4))
        );
    }
}
