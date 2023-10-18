package persistence.dialect;

public interface Dialect {
    PagingStrategy getPagingStrategy();
    DBColumnTypeMapper getColumnTypeMapper();

}
