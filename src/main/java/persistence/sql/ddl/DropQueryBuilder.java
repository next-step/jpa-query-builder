package persistence.sql.ddl;

import persistence.sql.mapping.TableData;
import persistence.sql.mapping.TableExtractor;

public class DropQueryBuilder {
    private final TableData tableData;


    public DropQueryBuilder(Class<?> clazz) {
        this.tableData = new TableExtractor(clazz).createTable();
    }

    public String toQuery() {
        return String.format("DROP TABLE %s", tableData.getName());
    }
}
