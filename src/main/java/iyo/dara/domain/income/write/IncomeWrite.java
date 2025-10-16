package iyo.dara.domain.income.write;

import iyo.dara.domain.income.Income;

import java.util.List;

public record IncomeWrite (List<IncomeDto> incomes) {
    public record IncomeDto(
            String source,
            String account,
            double amount,
            String date
    ) {
        public static IncomeDto from(Income i) {
            return new IncomeDto(
                    i.source().name(),
                    i.account().name(),
                    i.amount(),
                    i.date().toString()
            );
        }
    }

    public static IncomeWrite from(List<Income> ixs) {
        return new IncomeWrite(ixs.stream()
                .map(IncomeDto::from)
                .toList());
    }
}
