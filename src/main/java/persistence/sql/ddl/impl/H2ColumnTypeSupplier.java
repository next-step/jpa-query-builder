package persistence.sql.ddl.impl;

import persistence.sql.ddl.Dialect;
import persistence.sql.ddl.QueryColumnSupplier;
import persistence.sql.node.FieldNode;

import java.sql.Types;
import java.util.Map;

/**
 * H2 데이터베이스 컬럼 타입 제공자
 */
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
