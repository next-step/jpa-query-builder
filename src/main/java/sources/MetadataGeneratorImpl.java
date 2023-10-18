package sources;

import exception.AnnotationException;
import jakarta.persistence.Id;

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
        String idName = findIdFieldName(entity);
        List<String> fieldsName = Arrays.stream(entity.getDeclaredFields())
                .map(Field::getName)
                .filter(fieldName -> !fieldName.equals(idName)).collect(Collectors.toList());

        return new MetaData(entityName, idName, fieldsName);
    }
    // 엔티티 클래스로 메타데이터를 만드는 메소드
    public MetaData generate() {

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
