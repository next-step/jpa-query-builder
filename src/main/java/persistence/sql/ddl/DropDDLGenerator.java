package persistence.sql.ddl;

public interface DropDDLGenerator {
    String generate(EntityFields entityFields);
}
