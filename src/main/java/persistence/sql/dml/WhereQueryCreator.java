package persistence.sql.dml;

import persistence.sql.dml.where.FetchWhereQueries;
import persistence.sql.dml.where.FetchWhereQuery;

import java.util.List;

public interface WhereQueryCreator {

    String createWhereQuery(FetchWhereQueries fetchWhereQueries);
}
