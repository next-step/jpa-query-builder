package persistence.sql.ddl.create;

import persistence.sql.ddl.create.component.column.ColumnComponentBuilder;
import persistence.sql.ddl.create.component.constraint.ConstraintComponentBuilder;

import java.util.ArrayList;
import java.util.List;

public class DdlCreateQueryBuilder {
    private static final String COMMA_NEW_LINE = ",\n";

    private final List<ColumnComponentBuilder> columnComponentBuilders = new ArrayList<>();
    private final List<ConstraintComponentBuilder> constraintComponentBuilders = new ArrayList<>();

    private DdlCreateQueryBuilder() {
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

    public String build(String entityClassName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("create table {TABLE_NAME} (\n");

        this.columnComponentBuilders.stream()
                .map(ColumnComponentBuilder::build)
                .forEach(builder -> stringBuilder.append(builder).append(COMMA_NEW_LINE));
        this.constraintComponentBuilders.stream()
                .map(ConstraintComponentBuilder::build)
                .forEach(builder -> stringBuilder.append(builder).append(COMMA_NEW_LINE));

        stringBuilder.setLength(stringBuilder.length() - 2);
        return stringBuilder.append("\n);").toString().replace("{TABLE_NAME}", entityClassName.toLowerCase());
    }
}
