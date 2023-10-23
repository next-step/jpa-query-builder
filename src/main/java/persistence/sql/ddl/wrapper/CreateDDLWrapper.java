package persistence.sql.ddl.wrapper;

import persistence.entity.attribute.GeneralAttribute;
import persistence.entity.attribute.id.IdAttribute;
import persistence.sql.ddl.converter.SqlConverter;

import java.util.List;
import java.util.stream.Collectors;

public class CreateDDLWrapper implements DDLWrapper {
    private final SqlConverter sqlConverter;

    public CreateDDLWrapper(SqlConverter sqlConverter) {
        this.sqlConverter = sqlConverter;
    }

    @Override
    public String wrap(String tableName, IdAttribute idAttribute, List<GeneralAttribute> generalAttributes) {
        return String.format("CREATE TABLE %s ( %s );", tableName, idAttribute.prepareDDL(sqlConverter) + ", "
                + generalAttributes.stream().map(generalAttribute -> generalAttribute.prepareDDL(sqlConverter))
                .collect(Collectors.joining(", ")));
    }
}
