package persistence.sql.ddl;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import persistence.sql.exception.NotIdException;
import persistence.sql.exception.NotSupportedIdException;

import java.lang.reflect.Field;
import java.util.Map;

public class Id {
    public static final String ID_AUTO_INCREMENT = "id INT AUTO_INCREMENT PRIMARY KEY";
    public static Map<GenerationType, String> idMap = Map.of(
            GenerationType.AUTO, ID_AUTO_INCREMENT,
            GenerationType.IDENTITY, ID_AUTO_INCREMENT,
            //GenerationType.SEQUENCE, "",
            GenerationType.TABLE, "id INT PRIMARY KEY, seq_value INT", // TODO: (질문) map 내의 String도 상수화 해야할까요?
            GenerationType.UUID, "id UUID PRIMARY KEY"
    );
    private final GenerationType type;

    public Id(Field field) {
        if (!field.isAnnotationPresent(jakarta.persistence.Id.class)) {
            throw new NotIdException();
        }
        this.type = getType(field);
    }

    private static GenerationType getType(Field field) {
        if (field.isAnnotationPresent(GeneratedValue.class)) {
            return field.getAnnotation(GeneratedValue.class).strategy();
        }
        return GenerationType.IDENTITY;
    }

    public String getQuery() {
        if (idMap.get(type) == null) {
            throw new NotSupportedIdException();
        }
        return idMap.get(type);
    }
}
