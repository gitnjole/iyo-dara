package iyo.dara.income.service;

import iyo.dara.account.domain.AccountType;
import iyo.dara.core.sheets.SheetsTransformer;
import iyo.dara.core.util.ParsingUtils;
import iyo.dara.income.domain.Income;
import iyo.dara.income.domain.Source;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("incomeTransformer")
public class IncomeTransformer implements SheetsTransformer<Income> {
    @Override
    public Income fromSheet(List<Object> row) {
        return new Income(
                Source.valueOf(ParsingUtils.getString(row, 0)),
                AccountType.valueOf(ParsingUtils.getString(row, 1)),
                ParsingUtils.parseCost(ParsingUtils.getString(row, 2)),
                ParsingUtils.parseDate(ParsingUtils.getString(row, 3))
        );
    }
}
