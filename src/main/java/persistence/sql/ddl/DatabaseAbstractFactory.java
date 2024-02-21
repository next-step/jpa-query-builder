package persistence.sql.ddl;

public interface DatabaseAbstractFactory {

    H2QueryBuilder createH2Database();
}
