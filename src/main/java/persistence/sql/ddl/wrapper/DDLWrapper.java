package persistence.sql.ddl.wrapper;

import persistence.sql.attribute.GeneralAttribute;
import persistence.sql.attribute.id.IdAttribute;

import java.util.List;

public interface DDLWrapper {
    String wrap(String tableName, IdAttribute idAttribute, List<GeneralAttribute> generalAttributes);
}
