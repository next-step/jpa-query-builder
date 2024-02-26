package persistence.sql.ddl.clause;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.meta.pk.GenerationType;
import persistence.sql.meta.pk.PKField;
import persistence.sql.meta.table.TableName;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Create {
    private final TableName tableName;
    private final PKField pkField;
    private final List<ColumnClause> columns;
    private final Dialect dialect;

    public Create(Class<?> clazz, Dialect dialect) {
        this.tableName = new TableName(clazz);
        this.pkField = new PKField(clazz);
        this.columns = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !(field.isAnnotationPresent(Transient.class) || field.isAnnotationPresent(Id.class)))
                .map(ColumnClause::new)
                .collect(Collectors.toList());
        this.dialect = dialect;
    }

    public String getTableName() {
        return tableName.getName();
    }

    public String getColumns() {
        StringBuilder sb = new StringBuilder();
        sb.append(
                String.format("    %s %s PRIMARY KEY,\n",
                        new ColumnClause(pkField.getField()).getSQL(dialect),
                        dialect.getPKGenerationType(GenerationType.of(pkField.getField()))
                )
        );
        columns.forEach(column -> {
            sb.append("    ");
            sb.append(column.getSQL(dialect));
            sb.append(",\n");
        });
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
