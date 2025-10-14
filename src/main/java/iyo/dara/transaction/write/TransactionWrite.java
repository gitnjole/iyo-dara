package iyo.dara.transaction.write;

import iyo.dara.transaction.domain.Transaction;

import java.util.List;

public record TransactionWrite(List<TransactionDto> transactions) {

    public record TransactionDto(
            String detail,
            String category,
            String subcategory,
            double cost,
            String date,
            String paymentSource,
            boolean impulse
    ) {
        public static TransactionDto from(Transaction t) {
            return new TransactionDto(
                    t.detail(),
                    t.category().name(),
                    t.subcategory().name(),
                    t.cost(),
                    t.date().toString(),
                    t.account().name(),
                    t.impulse()
            );
        }
    }

    public static TransactionWrite from(List<Transaction> txs) {
        return new TransactionWrite(txs.stream()
                .map(TransactionDto::from)
                .toList());
    }
}

