package persistence.sql.ddl;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

public class PrimaryKey {

    private final TableColumn tableColumn;
    private final GenerationType generationType;

    public PrimaryKey(TableColumn tableColumn, GeneratedValue generatedValue) {
        this.tableColumn = tableColumn;
        this.generationType = generatedValue == null ? GenerationType.AUTO : generatedValue.strategy();
    }

    public String name() {
        return tableColumn.name();
    }

    public GenerationType generationType() {
        return generationType;
    }
}
