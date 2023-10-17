package persistence.sql.ddl;

import persistence.sql.ddl.utils.Column;
import persistence.sql.ddl.utils.Columns;
import persistence.sql.ddl.utils.Table;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DdlQueryBuilder {
    private EntityMetaDataExtractor entityMetaDataExtractor;
    public final static String SPACE = " ";

    public DdlQueryBuilder(final Class<?> clazz) {
        this.entityMetaDataExtractor = new EntityMetaDataExtractor(clazz);
    }

    public String create(Table table) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("create ");
        stringBuilder.append(table.getName());
        stringBuilder.append("(");


        stringBuilder.append(this.entityMetaDataExtractor.getTable().getName());
        return "create person(id bigint primary key , name )";
    }

    public List<String> createColumnsDdl() {
        return entityMetaDataExtractor.getColumns().stream()
                .map(column -> column.getName() + SPACE + getType(column))
                .collect(Collectors.toList());
    }

    private String getType(Column column) {
        return column.getTypeName() + SPACE + getTypeLength(column);
    }

    private String getTypeLength(Column column) {
        return Optional.ofNullable(column)
                .map(c -> "(" + c.getLength() + ")")
                .orElse("");
    }
}
