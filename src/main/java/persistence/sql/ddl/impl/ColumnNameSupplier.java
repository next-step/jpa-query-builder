package persistence.sql.ddl.impl;

import jakarta.persistence.Column;
import persistence.sql.ddl.QueryColumnSupplier;
import persistence.sql.ddl.node.FieldNode;
import persistence.sql.ddl.util.NameConverter;

public class ColumnNameSupplier implements QueryColumnSupplier {
    private final short priority;
    private final NameConverter nameConverter;

    public ColumnNameSupplier(short priority, NameConverter nameconverter) {
        this.priority = priority;
        this.nameConverter = nameconverter;
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
        Column anno = fieldNode.getAnnotation(Column.class);

        if (anno == null || anno.name().isBlank()) {
            return nameConverter.convert(fieldNode.getFieldName());
        }

        return nameConverter.convert(anno.name());
    }
}
