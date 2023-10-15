package persistence.sql.ddl;

import jakarta.persistence.Id;
import persistence.sql.util.StringConstant;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnBuilder {

    private static final String PRIMARY_KEY = "PRIMARY KEY";
    private static final String COLUMN_JOIN = ", ";

    public static String getColumnDefinition(Field[] fields) {
        return String.join(COLUMN_JOIN, toSql(fields));
    }

    private static List<String> toSql(Field[] fields) {
        return Arrays.stream(fields)
                .map(ColumnBuilder::toSql)
                .collect(Collectors.toList());
    }

    private static String toSql(Field field) {
        return new StringBuilder()
                .append(field.getName().toLowerCase())
                .append(StringConstant.BLANK)
                .append(getSqlType(field.getType()))
                .append(getPrimaryKey(field))
                .toString();
    }

    private static String getSqlType(Class<?> type) {
        return ColumnType.getSqlType(type);
    }

    private static String getPrimaryKey(Field field) {
        Id pkAnnotation = field.getDeclaredAnnotation(Id.class);
        if (pkAnnotation == null) {
            return StringConstant.EMPTY_STRING;
        }
        return StringConstant.BLANK + PRIMARY_KEY;
    }


}
