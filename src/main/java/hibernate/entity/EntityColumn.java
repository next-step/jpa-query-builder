package hibernate.entity;

public interface EntityColumn {

    String getFieldName();

    ColumnType getColumnType();

    boolean isNullable();

    boolean isId();
}
