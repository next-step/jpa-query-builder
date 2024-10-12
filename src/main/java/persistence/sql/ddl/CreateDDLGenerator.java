package persistence.sql.ddl;

public interface CreateDDLGenerator {
    String generate(EntityFields entityFields);
}
