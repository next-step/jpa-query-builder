package persistence.sql.dml;

import kotlin.jvm.Transient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class QueryBuilder {

    Logger logger = LoggerFactory.getLogger(QueryBuilder.class);
    Class<?> clazz;
    public QueryBuilder(Class<?> clazz) {
        this.clazz = clazz;
    }
    public String getColumnPart() {
        for (Field field : clazz.getDeclaredFields()) {
            logger.debug(String.valueOf(field));
            return String.valueOf(field);
        }

        return "";
    }

    public String getColumnPart2() {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(field -> getColumnName(field))
                .collect(Collectors.joining(", "));
    }

    public String getColumnName(Field field) {
        field.getName();
        return "";
    }

    public String getValuePart() {

        return "";
    }
    private String columnsClause(Class<?> clazz) {

        return "";
    }

    private String valueClause(Object object) {

        return "";
    }
}
