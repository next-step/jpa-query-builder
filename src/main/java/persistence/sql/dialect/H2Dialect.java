package persistence.sql.dialect;

import persistence.sql.constant.ClassType;
import persistence.sql.constant.ColumnType;

public class H2Dialect extends Dialect {

    @Override
    public String convertDataType(Class<?> clazz) {
        ClassType classType = ClassType.valueOf(clazz.getSimpleName().toUpperCase());
        ColumnType type;

        switch (classType) {
            case STRING:
                type = ColumnType.VARCHAR;
                break;
            case INTEGER:
                type = ColumnType.INTEGER;
                break;
            case LONG:
                type = ColumnType.BIGINT;
                break;
            case BOOLEAN:
                type = ColumnType.BOOLEAN;
                break;
            default:
                throw new UnsupportedClassTypeException();
        }

        return type.getSql();
    }
}
