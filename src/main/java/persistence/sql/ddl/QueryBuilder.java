package persistence.sql.ddl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QueryBuilder {

    private final String entityName;
    private final String alias;
    private final List<String> projections;

    public QueryBuilder(String entityName, String alias) {
        this.entityName = entityName;
        this.alias = alias;
        projections = new ArrayList<>();
    }

    public void createBuild(StringBuilder sb, Map<String, Object> updateParamValues) {
        sb.append("create ").append(entityName).append(" ");

    }
}
