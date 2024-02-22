package persistence.sql.dialect.identity;

public interface IdentityColumnSupport {

    String getIdentityColumnString();

    String getIdentityInsertString();

    default boolean hasIdentityInsertKeyword() {
        return this.getIdentityInsertString() != null;
    }

}
