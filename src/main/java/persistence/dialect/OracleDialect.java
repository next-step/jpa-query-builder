package persistence.dialect;

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
