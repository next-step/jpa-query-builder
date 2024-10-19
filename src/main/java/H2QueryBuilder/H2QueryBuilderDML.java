package H2QueryBuilder;

import repository.QueryBuilderDML;

public class H2QueryBuilderDML implements QueryBuilderDML {
    private final static String INSERT_QUERY = "insert into %s (%s)";
    private final static String INSERT_QUERY_VALUES = "values (%s)";

    @Override
    public String insert(Object object) {
//        return generateInsertTableQuery(object);
        return "";
    }

//    private String generateInsertTableQuery(Object object) {
//
//    }
}
