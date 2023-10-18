package persistence.sql.ddl;

public class NoEntityException extends RuntimeException {
    public NoEntityException(String name) {
        super("no entity annotation. className: " + name);
    }
}
