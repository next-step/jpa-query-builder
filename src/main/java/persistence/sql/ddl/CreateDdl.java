package persistence.sql.ddl;

import jakarta.persistence.Transient;
import persistence.sql.column.*;
import persistence.sql.dialect.Database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CreateDdl implements DdlQueryBuilder {

    private static final String CREATE_TABLE_DDL = "create table %s (%s)";
    private static final String COMMA = ", ";

    @Override
    public String generate(Class<?> clazz, Database database) {

        ColumnGenerator columnGenerator = new ColumnGenerator(new GeneralColumnFactory());
        TableColumn tableColumn = TableColumn.from(clazz);
        String columns = columnGenerator.of(clazz.getDeclaredFields(), database.createDialect())
                .stream()
                .map(Column::getDefinition)
                .collect(Collectors.joining(COMMA));

        return String.format(CREATE_TABLE_DDL, tableColumn.getName(), columns);
    }
}
