package persistence.sql.ddl.wrapper;

import persistence.sql.ddl.attribute.GeneralAttribute;
import persistence.sql.ddl.attribute.id.IdAttribute;

import java.util.List;

public class DropDDLWrapper implements DDLWrapper {

    @Override
    public String wrap(String tableName, IdAttribute idAttribute, List<GeneralAttribute> generalAttributes) {
        return String.format("DROP TABLE %s;", tableName);
    }
}
