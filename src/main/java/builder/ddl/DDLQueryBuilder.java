package builder.ddl;

import java.util.List;

public interface DDLQueryBuilder {

    String buildQuery(String tableName, List<DDLColumnData> columns);

}
