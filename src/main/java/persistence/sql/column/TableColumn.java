package persistence.sql.column;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import persistence.sql.Type.NameType;
import persistence.sql.dialect.Database;

public class TableColumn {

    private final NameType name;
    private final Columns columns;
    private final Database database;

    public TableColumn(NameType name, Columns columns, Database database) {
        this.name = name;
        this.columns = columns;
        this.database = database;
    }

    public static TableColumn from(Class<?> clazz, Database database) {
        validateEntityAnnotation(clazz);
        NameType tableName = new NameType(clazz.getSimpleName());
        if (clazz.isAnnotationPresent(Table.class)) {
            tableName.setName(clazz.getAnnotation(Table.class).name());
        }
        Columns columns = Columns.of(clazz.getDeclaredFields(), database.createDialect());
        return new TableColumn(tableName, columns, database);
    }

    private static void validateEntityAnnotation(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("[INFO] No @Entity annotation");
        }
    }

    public String getName() {
        return changeSnakeCase(name.getValue());
    }

    public Columns getColumns() {
        return columns;
    }

    public Database getDatabase() {
        return database;
    }

    private String changeSnakeCase(String name) {
        StringBuilder tableName = new StringBuilder();
        for (int index = 0; index < name.length(); index++) {
            char ch = name.charAt(index);
            addUnderScore(index, ch, tableName);
            tableName.append(Character.toLowerCase(ch));
        }
        return tableName.toString();
    }

    private void addUnderScore(int index, char ch, StringBuilder tableName) {
        if (index > 0 && Character.isUpperCase(ch)) {
            tableName.append("_");
        }
    }
}
