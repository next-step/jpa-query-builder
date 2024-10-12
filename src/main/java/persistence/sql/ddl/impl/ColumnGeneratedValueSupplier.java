package persistence.sql.ddl.impl;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import persistence.sql.ddl.QueryColumnSupplier;
import persistence.sql.ddl.node.FieldNode;

/**
 * 컬럼 생성 시 @GeneratedValue 어노테이션을 처리하는 컬럼 쿼리 제공자
 */
public record ColumnGeneratedValueSupplier(short priority) implements QueryColumnSupplier {

    @Override
    public boolean supported(FieldNode fieldNode) {
        return fieldNode.getAnnotation(GeneratedValue.class) != null;
    }

    @Override
    public String supply(FieldNode fieldNode) {
        GeneratedValue anno = fieldNode.getAnnotation(GeneratedValue.class);
        if (anno == null || anno.strategy() != GenerationType.IDENTITY && anno.strategy() != GenerationType.AUTO) {
            return "";
        }

        return "AUTO_INCREMENT";
    }
}
