package persistence.sql.column;

public interface Column {

    String getDefinition();

    boolean isPk();

    String getColumnName();

}
