package persistence.sql.ddl;

import static persistence.sql.constant.SqlConstant.SPACE;
import static persistence.sql.constant.SqlConstant.EMPTY;
import persistence.sql.meta.Column;
import persistence.sql.dialect.Dialect;

public class FieldQueryGenerator {

    private final Dialect dialect;

    private static final String PRIMARY_KEY_DEFINITION = "PRIMARY KEY";
    private static final String NOTNULL_DEFINITION = "NOT NULL";


    private FieldQueryGenerator(Dialect dialect) {
        this.dialect = dialect;
    }

    public static FieldQueryGenerator from(Dialect dialect) {
        return new FieldQueryGenerator(dialect);
    }

    public String generate(Column field) {
        StringBuilder builder = new StringBuilder();

        builder.append(field.getColumnName());
        builder.append(SPACE.getValue());
        builder.append(getSqlType(field));
        builder.append(getGeneratedValue(field));
        builder.append(getPkConstraint(field));
        builder.append(getNotNullConstraint(field));

        return builder.toString();
    }

    private String getGeneratedValue(Column field) {
        if (field.isGeneratedValueAnnotation()) {
            return SPACE.getValue() + dialect.getAutoIncrementDefinition();
        }
        return EMPTY.getValue();
    }

    private String getSqlType(Column field) {
        Class<?> type = field.getType();
        return dialect.getSqlTypeDefinition(type);
    }

    private String getPkConstraint(Column field) {
        if (field.isIdAnnotation()) {
            return SPACE.getValue() + PRIMARY_KEY_DEFINITION;
        }
        return EMPTY.getValue();
    }

    private String getNotNullConstraint(Column field) {
        if (!field.isNullable()) {
            return SPACE.getValue() + NOTNULL_DEFINITION;
        }
        return EMPTY.getValue();
    }
}
