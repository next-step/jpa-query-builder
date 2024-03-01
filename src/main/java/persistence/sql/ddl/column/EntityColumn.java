package persistence.sql.ddl.column;

public interface EntityColumn {

    String BLANK = " ";

    default boolean hasId() {
        return false;
    }

    String defineColumn();

    String getName();
}
