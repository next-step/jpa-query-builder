package builder.dml;

import java.util.List;

public interface DMLQueryBuilder {
    //쿼리를 생성한다.
    String buildQuery(String tableName, List<DMLColumnData> columns, Object... id);

}
