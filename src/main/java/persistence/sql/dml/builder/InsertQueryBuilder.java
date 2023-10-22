package persistence.sql.dml.builder;

import persistence.sql.dml.ColumnValues;
import persistence.sql.meta.EntityMeta;
import persistence.sql.meta.MetaFactory;
import persistence.sql.util.StringConstant;

import java.util.List;

public class InsertQueryBuilder {

    private static final String INSERT_HEADER = "INSERT INTO ";
    private static final String VALUES = "VALUES";

    private final EntityMeta entityMeta;
    private final ColumnValues columnValues;

    private InsertQueryBuilder(EntityMeta entityMeta, ColumnValues columnValues) {
        this.entityMeta = entityMeta;
        this.columnValues = columnValues;
    }

    public static InsertQueryBuilder of(Object entityInstance) {
        EntityMeta entityMeta = MetaFactory.get(entityInstance.getClass());
        validateEntityAnnotation(entityMeta);
        return new InsertQueryBuilder(entityMeta, ColumnValues.ofFilteredAutoGenType(entityInstance));
    }

    private static void validateEntityAnnotation(EntityMeta entityMeta) {
        if (!entityMeta.isEntity()) {
            throw new IllegalArgumentException("Insert Query 빌드 대상이 아닙니다.");
        }
    }

    public String build() {
        return new StringBuilder()
                .append(INSERT_HEADER)
                .append(entityMeta.getTableName())
                .append(" (")
                .append(columnsClause(columnValues.columns()))
                .append(") ")
                .append(VALUES)
                .append(" (")
                .append(valuesClause(columnValues.values()))
                .append(");")
                .toString();
    }

    private String columnsClause(List<String> columns) {
        return String.join(StringConstant.COLUMN_JOIN, columns);
    }

    private String valuesClause(List<String> values) {
        return String.join(StringConstant.COLUMN_JOIN, values);
    }
}
