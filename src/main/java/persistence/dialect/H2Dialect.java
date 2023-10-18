package persistence.dialect;

public class H2Dialect implements Dialect {

    private final DBColumnTypeMapper dbColumnTypeMapper;

    public H2Dialect() {
        this.dbColumnTypeMapper = H2ColumnTypeMapper.getInstance();
    }

    @Override
    public DBColumnTypeMapper getColumnTypeMapper() {
        return this.dbColumnTypeMapper;
    }
}
