package iyo.dara.income.write;

import iyo.dara.income.domain.Income;

import java.util.List;

public record IncomeWrite (List<IncomeDto> incomes) {
    public record IncomeDto(
            String source,
            String paymentSource,
            double amount,
            String date
    ) {
        public static IncomeDto from(Income i) {
            return new IncomeDto(
                    i.source().name(),
                    i.paymentSource().name(),
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
