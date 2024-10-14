package persistence.sql.ddl.type;

import jakarta.persistence.GenerationType;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum TablePrimaryKeyGenerateType {

    IDENTITY(GenerationType.IDENTITY, SqlIdGenerationType.AUTO_INCREMENT);

    private final GenerationType generationType;
    private final String sqlType;
    private static final Map<GenerationType, TablePrimaryKeyGenerateType> keyGenerateTypes;

    static {
        keyGenerateTypes = Arrays.stream(values())
                .collect(Collectors.toConcurrentMap(type -> type.generationType, reference -> reference));
    }

    TablePrimaryKeyGenerateType(GenerationType generationType, String sqlType) {
        this.generationType = generationType;
        this.sqlType = sqlType;
    }

    public static TablePrimaryKeyGenerateType lookup(GenerationType type) {
        if (!keyGenerateTypes.containsKey(type)) {
            throw new RuntimeException("class '" + type.name() + "' type not exist.");
        }
        return keyGenerateTypes.get(type);
    }

    public String sqlType() {
        return sqlType;
    }

}
