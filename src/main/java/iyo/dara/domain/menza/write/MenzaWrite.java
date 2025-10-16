package iyo.dara.domain.menza.write;

import iyo.dara.domain.menza.Menza;

import java.util.List;

public record MenzaWrite(List<MenzaDto> menzas) {
    public record MenzaDto(
            String store,
            double cost,
            String date,
            String account
    ) {
        public static MenzaDto from(Menza m) {
            return new MenzaDto(
                    m.store(),
                    m.cost(),
                    m.date().toString(),
                    m.account().name()
            );
        }
    }
}
