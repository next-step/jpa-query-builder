package persistence.sql.ddl;

import persistence.sql.ddl.mapper.ConstraintMapper;
import persistence.sql.ddl.mapper.H2ConstraintMapper;
import persistence.sql.ddl.mapper.H2TypeMapper;
import persistence.sql.ddl.mapper.TypeMapper;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CreateQueryBuilder {

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE %s (%s);";

    private final TypeMapper typeMapper = new H2TypeMapper();
    private final ConstraintMapper constraintMapper = new H2ConstraintMapper();

    private final Columns columns;
    private final Table table;

    public CreateQueryBuilder(Class<?> clazz) {
        this.columns = new Columns(Arrays.stream(clazz.getDeclaredFields())
                .map(field -> new Column(field, typeMapper, constraintMapper)).collect(Collectors.toList()));
        this.table = new Table(clazz);
    }

    public String build() {
        return String.format(
                CREATE_TABLE_QUERY,
                table.getName(),
                columns.generateColumns());
    }

}
