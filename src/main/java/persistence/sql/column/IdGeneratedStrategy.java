package persistence.sql.column;

public interface IdGeneratedStrategy {

    String getValue();

    boolean isAutoIncrement();
}
