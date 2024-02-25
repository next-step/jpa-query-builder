package persistence.sql.dialect;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class DialectResolutionInfo implements DialectResolution {
    private final DatabaseMetaData databaseMetaData;

    public DialectResolutionInfo(final DatabaseMetaData databaseMetaData) {
        this.databaseMetaData = databaseMetaData;
    }

    @Override
    public String getDatabaseName() {
        try {
            return this.databaseMetaData.getDatabaseProductName();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
