package persistence.sql.column;

import jakarta.persistence.Transient;
import persistence.sql.dialect.Dialect;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class ColumnGenerator {

    private final GeneralColumnFactory columnFactory;
    public ColumnGenerator(GeneralColumnFactory columnFactory) {
        this.columnFactory = columnFactory;
    }

    public List<Column> of(Field[] fields, Dialect dialect) {
        return Arrays.stream(fields)
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(field -> {
                    Column column = columnFactory.create(field, dialect);
                    if(columnFactory.canCreatePkColumn(field)) {
                        column = columnFactory.createPkColumn(column, field, dialect);
                    }
                    return column;
                })
                .collect(Collectors.toList());
    }

}
