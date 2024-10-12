package persistence.sql.ddl.impl;

import jakarta.persistence.Table;
import persistence.sql.ddl.QueryBuilderHelper;
import persistence.sql.ddl.Types;
import persistence.sql.ddl.node.EntityNode;
import persistence.sql.ddl.node.FieldNode;

import java.util.List;
import java.util.Map;

public class H2QueryBuilder extends QueryBuilderHelper {
    private static final Map<Types, String> columnTypeMap = Map.of(
            Types.INTEGER, "INT",
            Types.BIGINT, "BIGINT",
            Types.VARCHAR, "VARCHAR"
    );
    public static final String DEFAULT_FIELD_TYPE = "VARCHAR(255)";

    @Override
    public <T> String buildCreateTableQuery(EntityNode<T> entityNode) {
        Class<T> entityClass = entityNode.getEntityClass();
        String tableName = entityClass.getSimpleName();

        if (entityClass.isAnnotationPresent(Table.class)) {
            tableName = entityClass.getAnnotation(Table.class).name();
        }

        return "CREATE TABLE " + camelToSnake(tableName);
    }

    @Override
    public String buildPrimaryKeyQuery(List<FieldNode> fieldNodes) {
        FieldNode primaryFieldNode = fieldNodes.stream()
                .filter(FieldNode::isPrimaryKey)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No primary key found"));

        return "PRIMARY KEY (%s)".formatted(camelToSnake(primaryFieldNode.getFieldName()));
    }

    @Override
    public String buildColumnQuery(FieldNode fieldNode) {
        String columnType = getColumnType(fieldNode);

        return "%s %s".formatted(camelToSnake(fieldNode.getFieldName()), columnType);
    }

    private String getColumnType(FieldNode fieldNode) {
        Types fieldType = fieldNode.getFieldType();
        String columnType = columnTypeMap.getOrDefault(fieldType, DEFAULT_FIELD_TYPE);

        if (columnType == null) {
            throw new IllegalArgumentException("Unsupported field type: " + fieldType);
        }

        if (Types.VARCHAR == fieldType) {
            // TODO: Add support for custom length
            return columnType + "(255)";
        }

        return columnType;
    }

    @Override
    public String buildConstraintQuery(FieldNode fieldNode) {
        // TODO: Implement this method
        return "";
    }
}
