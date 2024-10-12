package persistence.sql.ddl.impl;

import persistence.sql.ddl.QueryConstraintSupplier;
import persistence.sql.ddl.node.FieldNode;
import persistence.sql.ddl.util.NameConverter;

public class ColumnPrimaryKeySupplier implements QueryConstraintSupplier {
    private final short priority;
    private final NameConverter nameConverter;

    public ColumnPrimaryKeySupplier(short priority, NameConverter nameconverter) {
        this.priority = priority;
        this.nameConverter = nameconverter;
    }

    @Override
    public short priority() {
        return priority;
    }

    @Override
    public boolean supported(FieldNode fieldNode) {
        return fieldNode.isPrimaryKey();
    }

    @Override
    public String supply(FieldNode fieldNode) {
        return "PRIMARY KEY (%s)".formatted(nameConverter.convert(fieldNode.getFieldName()));
    }
}
