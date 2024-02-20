package persistence.sql.dialect;

import persistence.sql.dialect.identity.H2IdentityColumnSupport;
import persistence.sql.dialect.identity.IdentityColumnSupport;

import java.sql.Types;

public class H2Dialect implements Dialect {

    @Override
    public IdentityColumnSupport getIdentityColumnSupport() {
        return new H2IdentityColumnSupport();
    }

    @Override
    public String convertColumnType(final int type, final int length) {

        switch (type) {
            case Types.VARCHAR: {
                return "varchar(" + length + ")";
            }
            case Types.BIGINT: {
                return "bigint";
            }
            case Types.INTEGER: {
                return "integer";
            }
        }

        throw new DialectException("NotFount ColumnType for " + type + " in H2Dialect");
    }

}
