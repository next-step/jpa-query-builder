package persistence.sql.ddl.dialect;

public interface Dialect {
    String getFieldDefinition(int type);
}
