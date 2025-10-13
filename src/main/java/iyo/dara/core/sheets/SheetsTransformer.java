package iyo.dara.core.sheets;

import java.util.List;

public interface SheetsTransformer<T> {
    T fromSheet(List<Object> row);
}
