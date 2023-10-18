package sources;

import exception.AnnotationException;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MetadataGeneratorImpl implements MetadataGenerator {

    private final AnnotationBinder annotationBinder;
    public MetadataGeneratorImpl(AnnotationBinder annotationBinder) {
        this.annotationBinder = annotationBinder;
    }

    @Override
    public MetaData generator(Class<?> entity) {
        //엔티티 이름(테이블 이름)
        String entityName = annotationBinder.entityBinder(entity);
        Field idField = findIdField(entity);
        String idName = annotationBinder.entityIdBinder(idField);
        List<ColumnMetaData> columns = Arrays.stream(entity.getDeclaredFields())
                .filter(field -> !field.equals(idField))
                .filter(this::isTransientField)
                .map(annotationBinder::columnBinder)
                .collect(Collectors.toList());

        return new MetaData(entityName, idName, columns);
    }

    private Field findIdField(Class<?> fromClass) {
        List<Field> fields = getFieldsInClass(fromClass);
        return fields.stream().filter(field -> field.isAnnotationPresent(Id.class)).findFirst().orElseThrow(() -> new AnnotationException("id 필드가 없습니다."));
    }

    private List<Field> getFieldsInClass(Class<?> fromClass) {
        return Arrays.stream(fromClass.getDeclaredFields()).collect(Collectors.toList());
    }

    private boolean isTransientField(Field field) {
        return !field.isAnnotationPresent(Transient.class);
    }

}
