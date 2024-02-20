package persistence.sql.column;

import static persistence.sql.column.MetaDataMapper.SPACE;

public class PkColumn implements Column {

    private static final String PRIMARY_KEY = "primary key";

    private final String name;
    private final ColumnType columnType;
    private final IdGeneratedStrategy idGeneratedStrategy;

    public PkColumn(String name, ColumnType columnType, IdGeneratedStrategy idGeneratedStrategy) {
        this.name = name;
        this.columnType = columnType;
        this.idGeneratedStrategy = idGeneratedStrategy;
    }


    @Override
    public String getDefinition() {
        return name + columnType.getColumnDefinition() + SPACE + idGeneratedStrategy.getValue() +
                SPACE + PRIMARY_KEY;
    }

}
