package persistence.sql.ddl.impl;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import persistence.sql.ddl.QueryColumnSupplier;
import persistence.sql.ddl.node.FieldNode;

import java.util.List;

/**
 * 컬럼 생성 시 @GeneratedValue 어노테이션을 처리하는 컬럼 쿼리 제공자
 */
public record ColumnGeneratedValueSupplier(short priority) implements QueryColumnSupplier {

    private static final List<GenerationType> ALLOW_AUTO_INCREMENT_STRATEGY = List.of(
            GenerationType.IDENTITY, GenerationType.AUTO
    );

    @Override
    public boolean supported(FieldNode fieldNode) {
        return fieldNode.getAnnotation(GeneratedValue.class) != null;
    }

    @Override
    public String supply(FieldNode fieldNode) {
        GeneratedValue anno = fieldNode.getAnnotation(GeneratedValue.class);
        if (appendableAutoIncrement(anno)) {
            return "AUTO_INCREMENT";
        }

        return "";
    }

    private static boolean appendableAutoIncrement(GeneratedValue anno) {
        return anno != null && ALLOW_AUTO_INCREMENT_STRATEGY.contains(anno.strategy());
    }
}
