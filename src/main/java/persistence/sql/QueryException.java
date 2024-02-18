package persistence.sql;

public class QueryException extends RuntimeException {

    public QueryException(String message) {
        super(message);
    }
}
