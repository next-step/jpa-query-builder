package persistence.sql.ddl;

public class DropQueryBuilder {
    public String dropTableQuery(Class<?> clazz) {
        return "drop table users";
    }
}
