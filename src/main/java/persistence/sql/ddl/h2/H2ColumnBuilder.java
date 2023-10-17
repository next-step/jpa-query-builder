package persistence.sql.ddl.h2;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import persistence.sql.ddl.ColumnBuilder;

import java.lang.reflect.Field;

import static persistence.sql.util.StringConstant.BLANK;
import static persistence.sql.util.StringConstant.EMPTY_STRING;

public class H2ColumnBuilder extends ColumnBuilder {

    private static final String AUTO_INCREMENT = "AUTO_INCREMENT";
    private static final String PRIMARY_KEY = "PRIMARY KEY";
    private static final String NOT_NULL = "NOT NULL";

    @Override
    protected String getSqlType(Class<?> type) {
        return H2ColumnType.getSqlType(type);
    }

    @Override
    protected String getGenerationStrategy(Field field) {
        GeneratedValue generatedValue = field.getDeclaredAnnotation(GeneratedValue.class);
        if (generatedValue == null) {
            return EMPTY_STRING;
        }
        if (generatedValue.strategy() == GenerationType.IDENTITY) {
            return BLANK + AUTO_INCREMENT;
        }
        return EMPTY_STRING;
    }

    @Override
    protected String getPrimaryKey(Field field) {
        Id pkAnnotation = field.getDeclaredAnnotation(Id.class);
        if (pkAnnotation != null) {
            return BLANK + PRIMARY_KEY;
        }
        return EMPTY_STRING;
    }

    @Override
    protected String getNullable(Field field) {
        Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
        if (columnAnnotation != null && !columnAnnotation.nullable()) {
            return BLANK + NOT_NULL;
        }
        return EMPTY_STRING;
    }

}
