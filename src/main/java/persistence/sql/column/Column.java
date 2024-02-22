package persistence.sql.column;

import persistence.sql.dialect.Dialect;

import java.lang.reflect.Field;

public interface Column {

    String getDefinition();

    Column convertPk(Field field, Dialect dialect);

    boolean isPk();

    String getColumnName();

}
