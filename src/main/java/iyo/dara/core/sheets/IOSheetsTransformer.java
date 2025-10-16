package iyo.dara.core.sheets;

import java.util.List;

public interface IOSheetsTransformer<T, D> extends SheetsTransformer<T> {
    T fromSheet(List<Object> row);
    List<Object> toSheet(T entity);
    T fromDto(D dto);
}
