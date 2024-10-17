package persistence.sql.dml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class QueryBuilder {

    Logger logger = LoggerFactory.getLogger(QueryBuilder.class);
    Class<?> clazz;
    public QueryBuilder(Class<?> clazz) {
        this.clazz = clazz;
    }
    public String getColumns() {
        for (Field field : clazz.getDeclaredFields()) {
            logger.debug(String.valueOf(field));
        }

        return "";
    }

    public String getValue() {

        return "";
    }
    private String columnsClause(Class<?> clazz) {

        return "";
    }

    private String valueClause(Object object) {

        return "";
    }
}
