package persistence.sql.ddl;

import persistence.sql.ddl.wrapper.Column;

public class FieldBuilder {

    private static FieldBuilder instance = null;
    private static final String SPACE = " ";
    private static final String EMPTY = "";
    private static final String PRIMARY_KEY_DEFINITION = "PRIMARY KEY";
    private static final String NOTNULL_DEFINITION = "NOT NULL";
    private static final String AUTO_INCREMENT_DEFINITION = "AUTO_INCREMENT";

    private FieldBuilder() {
    }

    public static synchronized FieldBuilder getInstance() {
        if (instance == null) {
            instance = new FieldBuilder();
        }
        return instance;
    }


    public String builder(Column field) {
        StringBuilder builder = new StringBuilder();

        builder.append(field.getColumnName());
        builder.append(SPACE);
        builder.append(getSqlType(field));
        builder.append(getGeneratedValue(field));
        builder.append(getPkConstraint(field));
        builder.append(getNotNullConstraint(field));

        return builder.toString();
    }

    private String getGeneratedValue(Column field) {
        if (field.isGeneratedValueAnnotation()) {
            return SPACE + AUTO_INCREMENT_DEFINITION;
        }
        return EMPTY;
    }

    private String getSqlType(Column field) {
        Class<?> type = field.getType();
        if (type.equals(String.class)) {
            return "VARCHAR";
        } else if (type.equals(Integer.class)) {
            return "INTEGER";
        } else if (type.equals(Long.class)) {
            return "BIGINT";
        } else {
            throw new IllegalArgumentException("지원하지 않은 타입입니다.: " + type);
        }
    }

    private String getPkConstraint(Column field) {
        if (field.isIdAnnotation()) {
            return SPACE + PRIMARY_KEY_DEFINITION;
        }
        return EMPTY;
    }

    private String getNotNullConstraint(Column field) {
        if (!field.isNullable()) {
            return SPACE + NOTNULL_DEFINITION;
        }
        return EMPTY;
    }
}
