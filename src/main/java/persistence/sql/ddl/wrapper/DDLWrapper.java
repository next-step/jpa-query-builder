package persistence.sql.ddl.wrapper;

import persistence.entity.attribute.GeneralAttribute;
import persistence.entity.attribute.id.IdAttribute;

import java.util.List;

public interface DDLWrapper {
    String wrap(String tableName, IdAttribute idAttribute, List<GeneralAttribute> generalAttributes);
}
