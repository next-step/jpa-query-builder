
package persistence.sql.ddl;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class JdbcServerExtension implements BeforeAllCallback, AfterAllCallback {

    private static DatabaseServer databaseServer;
    private static JdbcTemplate jdbcTemplate;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        databaseServer = new H2();
        databaseServer.start();

        jdbcTemplate = new JdbcTemplate(databaseServer.getConnection());
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        databaseServer.stop();;
    }

    public static JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
