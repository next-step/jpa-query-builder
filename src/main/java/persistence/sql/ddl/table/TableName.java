package persistence.sql.ddl.table;

import util.StringUtil;

public class TableName {

    private static final String DOT = "\\.";

    private final String name;

    private TableName(String name) {
        this.name = StringUtil.camelToSnake(name);
    }

    public static TableName from(Class<?> entity) {
        jakarta.persistence.Table table = entity.getAnnotation(jakarta.persistence.Table.class);

        if (table != null && !table.name().isEmpty()) {
            return new TableName(table.name());
        }

        String name = entity.getName();
        String[] splitByDotName = name.split(DOT);

        return new TableName(splitByDotName[splitByDotName.length - 1]);
    }

    public String getName() {
        return name;
    }
}
