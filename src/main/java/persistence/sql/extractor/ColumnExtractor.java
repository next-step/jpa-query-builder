package persistence.sql.extractor;

import jakarta.persistence.*;
import persistence.sql.ddl.KeyType;
import persistence.sql.extractor.exception.ColumExtractorCreateException;
import persistence.sql.extractor.exception.GenerationTypeMissingException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnExtractor {
    private final Column column;
    private final Field field;

    public ColumnExtractor(Field field) {
        if(field.isAnnotationPresent(Transient.class)) {
            throw new ColumExtractorCreateException("Transient 필드는 컬럼으로 생성할 수 없습니다.");
        }
        this.field = field;
        this.column = field.getAnnotation(Column.class);
    }

    public static List<ColumnExtractor> from(Class<?> entityClazz){
        return Arrays.stream(entityClazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(ColumnExtractor::new)
                .collect(Collectors.toList());
    }

    public String getName() {
        String columnName = field.getName();
        if(column != null && !column.name().isEmpty()) {
            columnName = column.name();
        }
        return columnName;
    }

    public Object getValue(Object entity) {
        try {
            field.setAccessible(true);
            return field.get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Class<?> getDataType() {
        return field.getType();
    }

    public boolean isNullable() {
        if(column == null){
            return true;
        }
        return column.nullable();
    }

    public boolean hasGenerationType() {
        return field.isAnnotationPresent(GeneratedValue.class);
    }

    public GenerationType getGenerationType() {
        GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
        if(generatedValue == null) {
            throw new GenerationTypeMissingException();
        }
        return generatedValue.strategy();
    }

    // TODO: Key 관련부분 하이버네이트 참고해보기
    public KeyType getKeyType() {
        if(isPrimaryKey()) {
            return KeyType.PRIMARY;
        }
        if(isUniqueKey()) {
            return KeyType.UNIQUE;
        }
        return null;
    }

    public boolean isPrimaryKey() {
        return field.isAnnotationPresent(Id.class);
    }

    private boolean isUniqueKey() {
        return column != null && column.unique();
    }
}
