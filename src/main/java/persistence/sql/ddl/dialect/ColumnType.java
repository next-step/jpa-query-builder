package persistence.sql.ddl.dialect;

import java.lang.reflect.Field;

public interface ColumnType {

    String longType();

    String stringType();

    String intType();

    String booleanType();

    String shortType();

    String generationIdentity();

    String getType(Field field);
}
