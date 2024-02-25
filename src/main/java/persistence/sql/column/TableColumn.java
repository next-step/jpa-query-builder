package persistence.sql.column;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import persistence.sql.type.NameType;

public class TableColumn {

    private final NameType name;

    public TableColumn(NameType name) {
        this.name = name;
    }

    public static TableColumn from(Class<?> clazz) {
        validateEntityAnnotation(clazz);
        NameType tableName = new NameType(clazz.getSimpleName());
        if (clazz.isAnnotationPresent(Table.class)) {
            tableName.setName(clazz.getAnnotation(Table.class).name());
        }
        return new TableColumn(tableName);
    }

    private static void validateEntityAnnotation(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("[INFO] No @Entity annotation");
        }
    }

    public String getName() {
        return changeSnakeCase(name.getValue());
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
