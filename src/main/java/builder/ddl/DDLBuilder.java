package builder.ddl;

import java.util.List;

public interface DDLBuilder {

    <T> String queryBuilder(DDLType ddlType, Class<T> clazz);

}
