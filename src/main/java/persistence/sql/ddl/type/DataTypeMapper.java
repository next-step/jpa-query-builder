package persistence.sql.ddl.type;

public interface DataTypeMapper {
    DataType getDataType(Class<?> clazz);
}
