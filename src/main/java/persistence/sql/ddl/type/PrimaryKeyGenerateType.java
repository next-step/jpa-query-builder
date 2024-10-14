package persistence.sql.ddl.type;

import jakarta.persistence.GenerationType;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum PrimaryKeyGenerateType {

    IDENTITY(GenerationType.IDENTITY, SqlIdGenerationType.AUTO_INCREMENT);

    private final GenerationType generationType;
    private final String sqlType;
    private static final Map<GenerationType, PrimaryKeyGenerateType> keyGenerateTypes;

    static {
        keyGenerateTypes = Arrays.stream(values())
                .collect(Collectors.toConcurrentMap(type -> type.generationType, reference -> reference));
    }

    PrimaryKeyGenerateType(GenerationType generationType, String sqlType) {
        this.generationType = generationType;
        this.sqlType = sqlType;
    }

    public static PrimaryKeyGenerateType lookup(GenerationType type) {
        if (!keyGenerateTypes.containsKey(type)) {
            throw new RuntimeException("class '" + type.name() + "' type not exist.");
        }
        return keyGenerateTypes.get(type);
    }

    public String sqlType() {
        return sqlType;
    }

}
