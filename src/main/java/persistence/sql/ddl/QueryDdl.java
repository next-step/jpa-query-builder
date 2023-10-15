package persistence.sql.ddl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryDdl {
    private static final Logger logger = LoggerFactory.getLogger(QueryDdl.class);

    public static <T> String create(Class<T> tClass) throws NullPointerException {
        CreateQuery createQuery = CreateQuery.initCreateQuery(tClass);
        String query = createQuery.crateQuery();

        logger.debug("create query : {}", query);

        return query;
    }

    public static <T> String drop(Class<T> tClass) throws NullPointerException {
        DropQuery dropQuery = DropQuery.initDropQuery(tClass);
        String query = dropQuery.dropQuery();

        logger.debug("drop query : {}", query);

        return query;
    }
}
