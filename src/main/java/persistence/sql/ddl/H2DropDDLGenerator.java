package persistence.sql.ddl;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class H2DropDDLGenerator implements DropDDLGenerator {
    @Override
    public String generate(Entity entity) {
        String name = entity.getName();

        return "DROP TABLE %s;".formatted(name);
    }
}
