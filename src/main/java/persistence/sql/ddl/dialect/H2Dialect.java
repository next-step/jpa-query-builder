package persistence.sql.ddl.dialect;

import jakarta.persistence.GenerationType;
import persistence.sql.ddl.constraint.Constraint;
import persistence.sql.ddl.constraint.NotNull;
import persistence.sql.ddl.constraint.PrimaryKey;
import persistence.sql.ddl.domain.Type;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class H2Dialect implements Dialect {

    private static final String EMPTY_STRING = "";
    private static final String SPACE = " ";

    private final Map<Type, String> types = new EnumMap<>(Type.class);
    private final Map<Class<?>, String> constraints = new HashMap<>();
    private final Map<GenerationType, String> generationTypes = new EnumMap<>(GenerationType.class);

    public H2Dialect() {
        registerTypes();
        registerConstraints();
        registerGenerationTypes();
    }

    private void registerTypes() {
        types.put(Type.TINYINT, "TINYINT");
        types.put(Type.SMALLINT, "SMALLINT");
        types.put(Type.INTEGER, "INTEGER");
        types.put(Type.VARCHAR, "VARCHAR");
        types.put(Type.BIGINT, "BIGINT");
    }

    private void registerConstraints() {
        constraints.put(PrimaryKey.class, "PRIMARY KEY");
        constraints.put(NotNull.class, "NOT NULL");
    }

    private void registerGenerationTypes() {
        generationTypes.put(GenerationType.AUTO, "AUTO_INCREMENT");
        generationTypes.put(GenerationType.IDENTITY, "AUTO_INCREMENT");
    }

    @Override
    public String getTypeString(Type type, int length) {
        return String.join(EMPTY_STRING,
                types.get(type),
                getTypeLength(type, length)
        );
    }

    private String getTypeLength(Type type, int length) {
        if (type.getDefaultLength() == null) {
            return EMPTY_STRING;
        }
        if (length == 0) {
            return "(" + type.getDefaultLength() + ")";
        }
        return "(" + length + ")";
    }

    @Override
    public String getConstraintString(Constraint constraint) {
        return Stream.of(constraints.get(constraint.getClass()),
                        getGenerationTypeString(constraint))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(SPACE));
    }

    private String getGenerationTypeString(Constraint constraint) {
        if (constraint instanceof PrimaryKey) {
            return getGenerationType((PrimaryKey) constraint);
        }
        return EMPTY_STRING;
    }

    private String getGenerationType(PrimaryKey primaryKey) {
        if (primaryKey.getGenerationType() == null) {
            return EMPTY_STRING;
        }
        return generationTypes.get(primaryKey.getGenerationType());
    }

}
