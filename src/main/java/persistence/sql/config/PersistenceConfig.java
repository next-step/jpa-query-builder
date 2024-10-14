package persistence.sql.config;

import database.DatabaseServer;
import database.H2;
import persistence.sql.common.util.CamelToSnakeConverter;
import persistence.sql.common.util.NameConverter;
import persistence.sql.ddl.QueryColumnSupplier;
import persistence.sql.ddl.QueryConstraintSupplier;
import persistence.sql.ddl.TableScanner;
import persistence.sql.ddl.impl.*;
import persistence.sql.dml.Database;
import persistence.sql.dml.EntityManager;
import persistence.sql.dml.impl.DefaultDatabase;
import persistence.sql.dml.impl.DefaultEntityManager;

import java.sql.SQLException;
import java.util.SortedSet;
import java.util.TreeSet;

public class PersistenceConfig {
    private static final PersistenceConfig INSTANCE = new PersistenceConfig();

    private DatabaseServer databaseServer;

    private PersistenceConfig() {}

    public static PersistenceConfig getInstance() {
        return INSTANCE;
    }

    public TableScanner tableScanner() {
        return new AnnotatedTableScanner();
    }

    public NameConverter nameConverter() {
        return CamelToSnakeConverter.getInstance();
    }

    public SortedSet<QueryColumnSupplier> columnQuerySuppliers() {
        SortedSet<QueryColumnSupplier> suppliers = new TreeSet<>();

        suppliers.add(new ColumnNameSupplier((short) 1, nameConverter()));
        suppliers.add(new H2ColumnTypeSupplier((short) 2, H2Dialect.create()));
        suppliers.add(new ColumnGeneratedValueSupplier((short) 3));
        suppliers.add(new ColumnOptionSupplier((short) 4));

        return suppliers;
    }

    private SortedSet<QueryConstraintSupplier> constraintQuerySuppliers() {
        SortedSet<QueryConstraintSupplier> suppliers = new TreeSet<>();

        suppliers.add(new ConstraintPrimaryKeySupplier((short) 1, nameConverter()));

        return suppliers;
    }

    public EntityManager entityManager() throws SQLException {
        return new DefaultEntityManager(database());
    }

    public Database database() throws SQLException {
        return new DefaultDatabase(databaseServer());
    }

    public DatabaseServer databaseServer() throws SQLException {
        if (databaseServer == null) {
            databaseServer = new H2();
            return databaseServer;
        }
        return databaseServer;
    }
}
