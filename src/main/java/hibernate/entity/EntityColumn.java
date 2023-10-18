package hibernate.entity;

import jakarta.persistence.GenerationType;

public interface EntityColumn {

    String getFieldName();

    ColumnType getColumnType();

    boolean isNullable();

    boolean isId();

    GenerationType getGenerationType();
}
