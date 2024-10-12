package persistence.sql.ddl.config;

import persistence.sql.ddl.QueryBuilder;
import persistence.sql.ddl.QueryColumnSupplier;
import persistence.sql.ddl.QueryConstraintSupplier;
import persistence.sql.ddl.TableScanner;
import persistence.sql.ddl.impl.*;
import persistence.sql.ddl.util.CamelToSnakeConverter;
import persistence.sql.ddl.util.NameConverter;

import java.util.SortedSet;
import java.util.TreeSet;

public class PersistenceConfig {
    private static final PersistenceConfig INSTANCE = new PersistenceConfig();

    private PersistenceConfig() {}

    public static PersistenceConfig getInstance() {
        return INSTANCE;
    }

    public TableScanner tableScanner() {
        return new AnnotatedTableScanner();
    }

    public QueryBuilder queryBuilder() {
        return new H2QueryBuilder(nameConverter(), columnQuerySuppliers(), constraintQuerySuppliers());
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

        suppliers.add(new ColumnPrimaryKeySupplier((short) 1, nameConverter()));

        return suppliers;
    }
}
