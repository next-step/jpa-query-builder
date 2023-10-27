package persistence.sql.ddl;

import java.lang.reflect.Field;

public interface FieldSQLGenerator {

    String generate(Field field);
}
