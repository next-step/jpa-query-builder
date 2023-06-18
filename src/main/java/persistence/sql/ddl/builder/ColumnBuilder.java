package persistence.sql.ddl.builder;

import persistence.sql.ddl.column.DdlColumn;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnBuilder {
    private static final String COLUMN_DELIMITER = ", ";
    private static final String COLUMN_ATTRIBUTE_DELIMITER = " ";

    private final List<DdlColumn> ddlColumns;

    public ColumnBuilder(List<DdlColumn> ddlColumns) {
        this.ddlColumns = Collections.unmodifiableList(ddlColumns);
    }

    public String build() {
        return ddlColumns.stream()
                .map(ColumnBuilder::build)
                .collect(Collectors.joining(COLUMN_DELIMITER));
    }

    private static String build(DdlColumn ddlColumn) {
        String name = ddlColumn.name();
        String type = ddlColumn.type();
        String options = ddlColumn.options();

        if (ddlColumn.options().isBlank()) {
            return String.join(COLUMN_ATTRIBUTE_DELIMITER, name, type);
        }
        return String.join(COLUMN_ATTRIBUTE_DELIMITER, name, type, options);
    }
}
