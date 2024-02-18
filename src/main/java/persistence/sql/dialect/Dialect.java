package persistence.sql.dialect;

public interface Dialect {

    String createColumnQuery(final String type);

    String getPk();

}
