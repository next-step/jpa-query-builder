package persistence.sql.ddl.impl;

import jakarta.persistence.Transient;
import persistence.sql.QueryBuilder;
import persistence.sql.common.util.CamelToSnakeConverter;
import persistence.sql.common.util.NameConverter;
import persistence.sql.data.QueryType;
import persistence.sql.ddl.QueryColumnSupplier;
import persistence.sql.ddl.QueryConstraintSupplier;
import persistence.sql.dml.MetadataLoader;
import persistence.sql.node.FieldNode;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class CreateQueryBuilder implements QueryBuilder {
    private final NameConverter nameConverter;
    private final SortedSet<QueryColumnSupplier> columnQuerySuppliers;
    private final SortedSet<QueryConstraintSupplier> constraintQuerySuppliers;

    public CreateQueryBuilder(NameConverter nameConverter,
                              SortedSet<QueryColumnSupplier> columnQuerySuppliers,
                              SortedSet<QueryConstraintSupplier> constraintQuerySuppliers) {
        this.nameConverter = nameConverter;
        this.columnQuerySuppliers = columnQuerySuppliers;
        this.constraintQuerySuppliers = constraintQuerySuppliers;
    }

    public static CreateQueryBuilder createDefault() {
        SortedSet<QueryColumnSupplier> columnQuerySuppliers = new TreeSet<>();
        SortedSet<QueryConstraintSupplier> constraintQuerySuppliers = new TreeSet<>();

        columnQuerySuppliers.add(new ColumnNameSupplier((short) 1, CamelToSnakeConverter.getInstance()));
        columnQuerySuppliers.add(new H2ColumnTypeSupplier((short) 2, H2Dialect.create()));
        columnQuerySuppliers.add(new ColumnGeneratedValueSupplier((short) 3));
        columnQuerySuppliers.add(new ColumnOptionSupplier((short) 4));

        constraintQuerySuppliers.add(new ConstraintPrimaryKeySupplier((short) 1, CamelToSnakeConverter.getInstance()));

        return new CreateQueryBuilder(CamelToSnakeConverter.getInstance(), columnQuerySuppliers, constraintQuerySuppliers);
    }

    @Override
    public QueryType queryType() {
        return QueryType.CREATE;
    }

    @Override
    public boolean supported(QueryType queryType) {
        return QueryType.CREATE.equals(queryType);
    }

    @Override
    public String build(MetadataLoader<?> loader, Object value) {
        String tableName = loader.getTableName();
        String columns = loader.getFieldAllByPredicate(field -> !field.isAnnotationPresent(Transient.class))
                .stream().map(FieldNode::from)
                .map(this::buildColumnQuery)
                .collect(Collectors.joining(", "));

        String constraints = loader.getFieldAllByPredicate(field -> !field.isAnnotationPresent(Transient.class))
                .stream().map(FieldNode::from)
                .map(this::buildConstraintQuery)
                .filter(s -> !s.isBlank())
                .collect(Collectors.joining(", "));

        return "CREATE TABLE %s (%s, %S);".formatted(nameConverter.convert(tableName), columns, constraints);
    }

    private String buildColumnQuery(FieldNode fieldNode) {
        return columnQuerySuppliers.stream().filter(supplier -> supplier.supported(fieldNode))
                .map(supplier -> supplier.supply(fieldNode).trim()).collect(Collectors.joining(" "));
    }

    private String buildConstraintQuery(FieldNode fieldNode) {
        List<QueryConstraintSupplier> filteredSuppliers = constraintQuerySuppliers.stream()
                .filter(supplier -> supplier.supported(fieldNode)).toList();

        if (filteredSuppliers.isEmpty()) {
            return "";
        }

        return filteredSuppliers.stream()
                .map(supplier -> supplier.supply(fieldNode).trim())
                .collect(Collectors.joining(" , "));
    }
}
