package persistence.sql.model;

import java.util.List;

public class Table {

    private final String name;

    private final List<Column> columns;

    public Table(EntityAnalyzer analyzer) {
        this.name = analyzer.analyzeTableName();
        this.columns = analyzer.analyzeColumns();
    }

    public String getName() {
        return name;
    }

    public List<Column> getColumns() {
        return columns;
    }
}
