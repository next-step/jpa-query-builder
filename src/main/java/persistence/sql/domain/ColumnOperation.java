package persistence.sql.domain;

public interface ColumnOperation {

    String getJdbcColumnName();

    String getColumnValue();

    boolean hasColumnValue();

    Class<?> getColumnObjectType();

    Integer getColumnSize();

    boolean isPrimaryColumn();

    boolean isNullable();

    String getJavaFieldName();
}
