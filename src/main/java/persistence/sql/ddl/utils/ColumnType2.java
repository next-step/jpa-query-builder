package persistence.sql.ddl.utils;

import jakarta.persistence.GenerationType;

public interface ColumnType2 {

    String getName();

    boolean isId();

    boolean isNullable();

    boolean isTransient();

    int getLength();

}
