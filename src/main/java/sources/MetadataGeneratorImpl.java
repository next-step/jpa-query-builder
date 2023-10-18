package sources;

import exception.AnnotationException;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
        Map<String, String> fields = Arrays.stream(entity.getDeclaredFields())
                .filter(field -> !field.equals(idField))
                .collect(Collectors.toMap( annotationBinder::columnBinder, field -> field.getType().getSimpleName()));

        return new MetaData(entityName, idName, fields);
    }

    private Field findIdField(Class<?> fromClass) {
        List<Field> fields = getFieldsInClass(fromClass);
        return fields.stream().filter(field -> field.isAnnotationPresent(Id.class)).findFirst().orElseThrow(() -> new AnnotationException("id 필드가 없습니다."));
    }

    private List<Field> getFieldsInClass(Class<?> fromClass) {
        return Arrays.stream(fromClass.getDeclaredFields()).collect(Collectors.toList());
    }


}
