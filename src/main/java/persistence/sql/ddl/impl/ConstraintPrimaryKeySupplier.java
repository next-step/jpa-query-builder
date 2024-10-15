package persistence.sql.ddl.impl;

import persistence.sql.common.util.NameConverter;
import persistence.sql.ddl.QueryConstraintSupplier;
import persistence.sql.node.FieldNode;

/**
 * 컬럼 기본키 제약 조건 쿼리 제공자
 */
public class ConstraintPrimaryKeySupplier implements QueryConstraintSupplier {
    private final short priority;
    private final NameConverter nameConverter;

    public ConstraintPrimaryKeySupplier(short priority, NameConverter nameconverter) {
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
