package persistence.sql.ddl.type;

import jakarta.persistence.GenerationType;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum IdGenerationTypeReference {

    IDENTITY(GenerationType.IDENTITY, SqlIdGenerationType.AUTO_INCREMENT);

    private final GenerationType generationType;
    private final String sqlType;
    private static final Map<GenerationType, String> sqlTypes;

    static {
        sqlTypes = Arrays.stream(values())
                .collect(Collectors.toConcurrentMap(reference -> reference.generationType, reference -> reference.sqlType));
    }

    IdGenerationTypeReference(GenerationType generationType, String sqlType) {
        this.generationType = generationType;
        this.sqlType = sqlType;
    }

    public static String getSqlType(GenerationType type) {
        if (!sqlTypes.containsKey(type)) {
            throw new RuntimeException("class '" + type.name() + "' sql type not exist.");
        }
        return sqlTypes.get(type);
    }

}
