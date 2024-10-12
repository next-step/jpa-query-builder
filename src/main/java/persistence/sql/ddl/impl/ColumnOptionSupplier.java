package persistence.sql.ddl.impl;

import jakarta.persistence.Column;
import persistence.sql.ddl.QueryColumnSupplier;
import persistence.sql.ddl.node.FieldNode;

public class ColumnOptionSupplier implements QueryColumnSupplier {
    private final short priority;

    public ColumnOptionSupplier(short priority) {
        this.priority = priority;
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
        StringBuilder constraint = new StringBuilder();

        if (isNotNull(fieldNode)) {
            constraint.append("NOT NULL");
        }

        if (isUnique(fieldNode)) {
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
