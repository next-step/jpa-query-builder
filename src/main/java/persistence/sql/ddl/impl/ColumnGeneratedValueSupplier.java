package persistence.sql.ddl.impl;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import persistence.sql.ddl.QueryColumnSupplier;
import persistence.sql.ddl.node.FieldNode;

public class ColumnGeneratedValueSupplier implements QueryColumnSupplier {
    private final short priority;

    public ColumnGeneratedValueSupplier(short priority) {
        this.priority = priority;
    }

    @Override
    public short priority() {
        return priority;
    }

    @Override
    public boolean supported(FieldNode fieldNode) {
        return fieldNode.getAnnotation(GeneratedValue.class) != null;
    }

    @Override
    public String supply(FieldNode fieldNode) {
        GeneratedValue anno = fieldNode.getAnnotation(GeneratedValue.class);
        if(anno == null || anno.strategy() != GenerationType.IDENTITY && anno.strategy() != GenerationType.AUTO) {
            return "";
        }

        return "AUTO_INCREMENT";
    }
}
