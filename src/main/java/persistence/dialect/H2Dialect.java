package persistence.dialect;

public class H2Dialect implements Dialect {

    private final PagingStrategy pagingStrategy;
    private final DBColumnTypeMapper dbColumnTypeMapper;

    public H2Dialect() {
        this.pagingStrategy = DefaultPagingStrategy.getInstance();
        this.dbColumnTypeMapper = H2ColumnTypeMapper.getInstance();
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
