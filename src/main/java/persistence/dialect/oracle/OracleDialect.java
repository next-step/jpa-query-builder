package persistence.dialect.oracle;

import persistence.dialect.DBColumnTypeMapper;
import persistence.dialect.Dialect;
import persistence.dialect.PagingStrategy;
import persistence.dialect.RownumPagingStrategy;

public class OracleDialect implements Dialect {
    private final PagingStrategy pagingStrategy;
    private final DBColumnTypeMapper dbColumnTypeMapper;

    public OracleDialect() {
        this.pagingStrategy = RownumPagingStrategy.getInstance();
        this.dbColumnTypeMapper = OracleColumnTypeMapper.getInstance();
    }

    @Override
    public PagingStrategy getPagingStrategy() {
        return this.pagingStrategy;
    }

    @Override
    public DBColumnTypeMapper getColumnTypeMapper() {
        return this.dbColumnTypeMapper;
    }
}
