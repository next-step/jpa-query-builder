package persistence.sql.ddl.impl;

import persistence.sql.ddl.Dialect;
import persistence.sql.ddl.QueryColumnSupplier;
import persistence.sql.ddl.node.FieldNode;

import java.sql.Types;
import java.util.Map;

public class H2ColumnTypeSupplier implements QueryColumnSupplier {
    private static final Map<Class<?>, Integer> columnTypeMap = Map.of(
            Integer.class, Types.INTEGER,
            Long.class, Types.BIGINT,
            String.class, Types.VARCHAR
    );

    private final short priority;
    private final Dialect dialect;

    public H2ColumnTypeSupplier(short priority, Dialect dialect) {
        this.priority = priority;
        this.dialect = dialect;
    }

    @Override
    public short priority() {
        return priority;
    }

    @Override
    public boolean supported(FieldNode fieldNode) {
        return fieldNode != null;
    }

    @Override
    public String supply(FieldNode fieldNode) {
        Class<?> fieldType = fieldNode.getFieldType();
        Integer columnTypeIndex = columnTypeMap.get(fieldType);
        String columnType = dialect.get(columnTypeIndex);

        if (columnType == null) {
            throw new IllegalArgumentException("Unsupported field type: " + fieldType);
        }

        if (Types.VARCHAR == columnTypeIndex) {
            // TODO: Add support for custom length
            return columnType + "(255)";
        }

        return columnType;
    }
}
