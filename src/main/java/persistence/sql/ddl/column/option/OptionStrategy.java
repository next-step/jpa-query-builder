package persistence.sql.ddl.column.option;

import java.lang.reflect.Field;

public interface OptionStrategy {
    String DELIMITER = " ";

    Boolean supports(Field field);

    String options(Field field);
}
