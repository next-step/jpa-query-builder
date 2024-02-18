package persistence.sql.ddl.strategy;

import java.lang.reflect.Field;

public interface AdditionalColumQueryStrategy {

    boolean isRequired(Field field);

    String fetchQueryPart();
}
