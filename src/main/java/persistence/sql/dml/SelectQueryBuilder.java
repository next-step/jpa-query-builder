package persistence.sql.dml;

import persistence.sql.ddl.EntityMetaData;

public class SelectQueryBuilder {
    private static final String SELECT_QUERY_FORMAT = "select %s from %s";


    public String findAll(final EntityMetaData entityMetaData) {
        return String.format(SELECT_QUERY_FORMAT , "*" , entityMetaData.getTableName());
    }

}
