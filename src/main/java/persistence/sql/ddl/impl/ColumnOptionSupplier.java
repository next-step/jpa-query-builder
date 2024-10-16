package persistence.sql.ddl.impl;

import jakarta.persistence.Column;
import persistence.sql.ddl.QueryColumnSupplier;
import persistence.sql.node.FieldNode;

/**
 * 컬럼 옵션을 처리하는 컬럼 쿼리 제공자
 */
public record ColumnOptionSupplier(short priority) implements QueryColumnSupplier {

    @Override
    public boolean supported(FieldNode fieldNode) {
        return fieldNode != null;
    }

    @Override
    public String supply(FieldNode fieldNode) {
        StringBuilder constraint = new StringBuilder();

        if (isNotNull(fieldNode)) {
            constraint.append("NOT NULL");
        }

        if (isUnique(fieldNode)) {
            if (!constraint.isEmpty()) {
                constraint.append(" ");
            }
            constraint.append("UNIQUE");
        }

        return constraint.toString();
    }

    private boolean isUnique(FieldNode fieldNode) {
        Column anno = fieldNode.getAnnotation(Column.class);

        return anno != null && anno.unique();
    }

    private boolean isNotNull(FieldNode fieldNode) {
        Column anno = fieldNode.getAnnotation(Column.class);

        return anno != null && !anno.nullable() || fieldNode.isPrimaryKey();
    }
}
