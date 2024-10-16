package persistence.sql;

import persistence.sql.clause.Clause;
import persistence.sql.common.util.CamelToSnakeConverter;
import persistence.sql.data.QueryType;
import persistence.sql.ddl.impl.CreateQueryBuilder;
import persistence.sql.ddl.impl.DropQueryBuilder;
import persistence.sql.dml.MetadataLoader;
import persistence.sql.dml.impl.DeleteQueryBuilder;
import persistence.sql.dml.impl.InsertQueryBuilder;
import persistence.sql.dml.impl.SelectQueryBuilder;
import persistence.sql.dml.impl.UpdateQueryBuilder;

import java.util.EnumMap;

public class QueryBuilderFactory {
    private static final QueryBuilderFactory INSTANCE = new QueryBuilderFactory();

    private final EnumMap<QueryType, QueryBuilder> store = new EnumMap<>(QueryType.class);

    {
        store.put(QueryType.CREATE, CreateQueryBuilder.createDefault());
        store.put(QueryType.DROP, new DropQueryBuilder(CamelToSnakeConverter.getInstance()));
        store.put(QueryType.SELECT, new SelectQueryBuilder(CamelToSnakeConverter.getInstance()));
        store.put(QueryType.INSERT, new InsertQueryBuilder());
        store.put(QueryType.UPDATE, new UpdateQueryBuilder());
        store.put(QueryType.DELETE, new DeleteQueryBuilder(CamelToSnakeConverter.getInstance()));
    }

    private QueryBuilderFactory() {
    }

    public static QueryBuilderFactory getInstance() {
        return INSTANCE;
    }

    public String buildQuery(QueryType queryType, MetadataLoader<?> metadataLoader, Clause... clauses) {
        if (store.containsKey(queryType)) {
            return store.get(queryType).build(metadataLoader, clauses);
        }

        throw new IllegalArgumentException("Unsupported query type: " + queryType);
    }
}
