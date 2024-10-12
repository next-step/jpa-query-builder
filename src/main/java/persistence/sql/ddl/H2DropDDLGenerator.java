package persistence.sql.ddl;

public class H2DropDDLGenerator implements DropDDLGenerator {
    @Override
    public String generate(Entity entity) {
        String name = entity.getName();

        return "DROP TABLE %s;".formatted(name);
    }
}
