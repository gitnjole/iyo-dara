package iyo.dara.sheets;

import java.util.List;

public interface SheetsTransformer<T> {
    T fromSheet(List<Object> row);
}
