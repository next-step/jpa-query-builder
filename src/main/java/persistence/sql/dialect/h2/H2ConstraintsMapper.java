package persistence.sql.dialect.h2;

import persistence.sql.dialect.database.ConstraintsMapper;
import persistence.sql.dialect.exception.InvalidConstantTypeException;
import persistence.sql.entity.model.Constraints;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class H2ConstraintsMapper implements ConstraintsMapper {

    private final Map<Constraints, String> constantTypes;

    private H2ConstraintsMapper(final Map<Constraints, String> constantTypes) {
        this.constantTypes = constantTypes;
    }

    @Override
    public String getConstantType(Constraints constantType) {
        if (!constantTypes.containsKey(constantType)) {
            throw new InvalidConstantTypeException();
        }

        return constantTypes.get(constantType);
    }

    public static H2ConstraintsMapper newInstance() {
        Map<Constraints, String> workMap = new ConcurrentHashMap<>();

        workMap.put(Constraints.PK, "PRIMARY KEY AUTO_INCREMENT");
        workMap.put(Constraints.PRIMARY_KEY, "PRIMARY KEY");
        workMap.put(Constraints.NOT_NULL, "NOT NULL");

        return new H2ConstraintsMapper(workMap);
    }
}
