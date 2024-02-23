package persistence.sql.dml;

import persistence.sql.AbstractQueryBuilderFactory;
import persistence.sql.DatabaseDialect;

import java.util.Map;

public class DmlQueryBuilderFactory extends AbstractQueryBuilderFactory<DmlQueryBuilder> {

    public DmlQueryBuilderFactory() {
        super(Map.of(DatabaseDialect.MYSQL, new DmlQueryBuilder()));
    }
}
