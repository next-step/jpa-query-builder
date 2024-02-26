package persistence.sql.ddl;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import persistence.sql.exception.NotIdException;
import persistence.sql.exception.NotSupportedIdException;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Function;

public class Id {
    public static final String ID_AUTO_INCREMENT = "%s %s AUTO_INCREMENT PRIMARY KEY";
    public static Map<GenerationType, String> sqlMap = Map.of(
            GenerationType.AUTO, ID_AUTO_INCREMENT,
            GenerationType.IDENTITY, ID_AUTO_INCREMENT,
            GenerationType.TABLE, "%s %s PRIMARY KEY, seq_value INT",
            GenerationType.UUID, "%s UUID PRIMARY KEY"
    );

    public static final String INT = "INT";
    public static final String BIGINT = "BIGINT";
    private static final Map<Type, String> numberTypeMap = Map.of(
            Integer.class, INT,
            int.class, INT,
            Long.class, BIGINT,
            long.class, BIGINT,
            float.class, "FLOAT",
            double.class, "DOUBLE",
            byte.class, "TINYINT"
    );

    private final String name;
    private final String dataType;
    private final GenerationType generationType;

    public Id(Field field) {
        if (!field.isAnnotationPresent(jakarta.persistence.Id.class)) {
            throw new NotIdException();
        }
        this.name = field.getName();
        this.dataType = field.getType().getSimpleName();
        this.generationType = getType(field);
    }

    public String getName() {
        return this.name;
    }

    private static GenerationType getType(Field field) {
        if (field.isAnnotationPresent(GeneratedValue.class)) {
            return field.getAnnotation(GeneratedValue.class).strategy();
        }
        return GenerationType.IDENTITY;
    }

    public String getQuery() {
        if (sqlMap.get(generationType) == null) {
            throw new NotSupportedIdException();
        }
        if (generationType == GenerationType.UUID) {
            return String.format(sqlMap.get(generationType), name);
        }
        return String.format(sqlMap.get(generationType), name, dataType);
    }
}