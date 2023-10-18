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
        String idName = findIdFieldName(entity);
        Map<String, String> fields = Arrays.stream(entity.getDeclaredFields())
                .filter(field -> !field.getName().equals(idName))
                .collect(Collectors.toMap(field -> field.getType().getSimpleName(), annotationBinder::columnBinder));

        return new MetaData(entityName, idName, fields);
    }

    private String findIdFieldName(Class<?> fromClass) {
        List<Field> fields = getFieldsInClass(fromClass);
        Field idField = fields.stream().filter(field -> field.isAnnotationPresent(Id.class)).findFirst().orElseThrow(() -> new AnnotationException("id 필드가 없습니다."));
        return annotationBinder.entityIdBinder(idField);
    }

    private List<Field> getFieldsInClass(Class<?> fromClass) {
        return Arrays.stream(fromClass.getDeclaredFields()).collect(Collectors.toList());
    }


}
