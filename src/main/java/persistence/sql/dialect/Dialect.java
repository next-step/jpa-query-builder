package persistence.sql.dialect;

import jakarta.persistence.GenerationType;
import persistence.sql.constant.ColumnType;

public interface Dialect {

    String getTypeName(ColumnType columnType);

    String getGenerationStrategy(GenerationType generationType);

}
