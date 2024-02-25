package persistence.sql.ddl;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import persistence.sql.exception.NotIdException;
import persistence.sql.exception.NotSupportedIdException;

import java.lang.reflect.Field;
import java.util.Map;

public class Id {
    public static final String ID_AUTO_INCREMENT = "ID INT AUTO_INCREMENT PRIMARY KEY";
    public static Map<GenerationType, String> idMap = Map.of(
            GenerationType.AUTO, ID_AUTO_INCREMENT,
            GenerationType.IDENTITY, ID_AUTO_INCREMENT,
            //GenerationType.SEQUENCE, "",
            GenerationType.TABLE, "ID INT PRIMARY KEY, SEQ_VALUE INT", // TODO: (질문) map 내의 String도 상수화 해야할까요?
            GenerationType.UUID, "ID UUID PRIMARY KEY"
    );
    private final GenerationType type;

    public Id(Field field) {
        if (!field.isAnnotationPresent(jakarta.persistence.Id.class)) {
            throw new NotIdException();
        }
        this.type = field.getAnnotation(GeneratedValue.class).strategy();
    }

    public String getId() {
        if (idMap.get(type) == null) {
            throw new NotSupportedIdException();
        }
        return idMap.get(type);
    }
}
