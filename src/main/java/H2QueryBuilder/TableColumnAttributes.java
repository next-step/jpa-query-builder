package H2QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class TableColumnAttributes {
    private final List<TableColumnAttribute> tableColumnAttributes = new ArrayList<>();

    public TableColumnAttributes() {
    }

    public void add(TableColumnAttribute tableColumnAttribute) {
        tableColumnAttributes.add(tableColumnAttribute);

    }

    public List<TableColumnAttribute> getTableColumnAttributes() {
        return this.tableColumnAttributes;
    }
}
