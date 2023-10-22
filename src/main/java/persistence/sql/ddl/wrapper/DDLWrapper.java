package persistence.sql.ddl.wrapper;

import persistence.entitiy.attribute.GeneralAttribute;
import persistence.entitiy.attribute.id.IdAttribute;

import java.util.List;

public interface DDLWrapper {
    String wrap(String tableName, IdAttribute idAttribute, List<GeneralAttribute> generalAttributes);
}
