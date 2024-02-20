package persistence.sql.dialect;

import persistence.sql.dialect.identity.IdentityColumnSupport;

public interface Dialect {

    IdentityColumnSupport getIdentityColumnSupport();

    String convertColumnType(final int type, final int length);

}
