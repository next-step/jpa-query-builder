package persistence.sql.ddl.create.component;

import persistence.sql.ddl.create.component.column.ColumnComponentBuilder;
import persistence.sql.ddl.create.component.constraint.ConstraintComponentBuilder;

import java.util.ArrayList;
import java.util.List;

public class DdlCreateQueryBuilder {

    private final StringBuilder query = new StringBuilder();
    private final List<ColumnComponentBuilder> columnComponentBuilders = new ArrayList<>();
    private final List<ConstraintComponentBuilder> constraintComponentBuilders = new ArrayList<>();

    private DdlCreateQueryBuilder() {
        this.query
                .append("CREATE TABLE {TABLE_NAME} (\n");
    }

    public static DdlCreateQueryBuilder newInstance() {
        return new DdlCreateQueryBuilder();
    }

    public DdlCreateQueryBuilder add(ColumnComponentBuilder columnComponentBuilder) {
        this.columnComponentBuilders.add(columnComponentBuilder);
        return this;
    }

    public DdlCreateQueryBuilder add(List<ConstraintComponentBuilder> constraintComponentBuilders) {
        this.constraintComponentBuilders.addAll(constraintComponentBuilders);
        return this;
    }

    public String build(String tableName) {
        this.columnComponentBuilders.stream()
                .map(ColumnComponentBuilder::getComponentBuilder)
                .forEach(query::append);
        this.constraintComponentBuilders.stream()
                .map(ConstraintComponentBuilder::getComponentBuilder)
                .forEach(query::append);

        query.setLength(query.length() - 2);
        return query.append("\n);").toString().replace("{TABLE_NAME}", tableName.toLowerCase());
    }
}
