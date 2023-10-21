package persistence.sql.ddl.wrapper;

import persistence.sql.ddl.attribute.GeneralAttribute;
import persistence.sql.ddl.attribute.id.IdAttribute;

import java.util.List;
import java.util.stream.Collectors;

public class CreateDDLWrapper implements DDLWrapper {
    @Override
    public String wrap(String tableName, IdAttribute idAttribute, List<GeneralAttribute> generalAttributes) {
        return String.format("CREATE TABLE %s ( %s );", tableName, idAttribute.prepareDDL() + ", "
                + generalAttributes.stream().map(GeneralAttribute::prepareDDL)
                .collect(Collectors.joining(", ")));
    }
}
