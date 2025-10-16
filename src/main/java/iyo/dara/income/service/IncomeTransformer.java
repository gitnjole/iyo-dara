package iyo.dara.income.service;

import iyo.dara.account.domain.AccountType;
import iyo.dara.core.sheets.IOSheetsTransformer;
import iyo.dara.income.domain.Income;
import iyo.dara.income.domain.Source;
import iyo.dara.income.write.IncomeWrite;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static iyo.dara.core.util.ParsingUtils.*;
import static iyo.dara.core.util.ParsingUtils.getString;
import static iyo.dara.core.util.ParsingUtils.parseDate;
import static iyo.dara.core.util.ParsingUtils.parseEnum;

@Component("incomeTransformer")
public class IncomeTransformer implements IOSheetsTransformer<Income, IncomeWrite.IncomeDto> {
    @Override
    public Income fromSheet(List<Object> row) {
        return new Income(
                parseEnum(getString(row, 0), Source.class),
                parseEnum(getString(row, 1), AccountType.class),
                parseCost(getString(row, 2)),
                parseDate(getString(row, 3))
        );
    }

    @Override
    public List<Object> toSheet(Income i) {
        return List.of(
                i.source().name(),
                i.account().name(),
                formatCostForSheets(i.amount()),
                formatDate(i.date())
        );
    }

    @Override
    public Income fromDto(IncomeWrite.IncomeDto dto) {
        return new Income(
                Source.valueOf(dto.source()),
                AccountType.valueOf(dto.account()),
                dto.amount(),
                LocalDate.parse(dto.date())
        );
    }
}
