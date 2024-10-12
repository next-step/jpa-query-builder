package persistence.sql.ddl.impl;

import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import persistence.sql.ddl.QueryBuilder;
import persistence.sql.ddl.QueryColumnSupplier;
import persistence.sql.ddl.QueryConstraintSupplier;
import persistence.sql.ddl.node.EntityNode;
import persistence.sql.ddl.node.FieldNode;
import persistence.sql.ddl.util.NameConverter;

import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

public class H2QueryBuilder implements QueryBuilder {

    private final NameConverter nameConverter;
    private final SortedSet<QueryColumnSupplier> columnQuerySuppliers;
    private final SortedSet<QueryConstraintSupplier> constraintQuerySuppliers;

    public H2QueryBuilder(NameConverter nameConverter,
                          SortedSet<QueryColumnSupplier> columnQuerySuppliers,
                          SortedSet<QueryConstraintSupplier> constraintQuerySuppliers) {
        this.nameConverter = nameConverter;
        this.columnQuerySuppliers = columnQuerySuppliers;
        this.constraintQuerySuppliers = constraintQuerySuppliers;
    }

    @Override
    public String buildCreateTableQuery(EntityNode<?> entityNode) {
        StringBuilder query = new StringBuilder(buildTableQuery(entityNode));
        query.append(" (");
        buildColumnQuery(entityNode, query);
        buildConstraintQuery(entityNode, query);
        query.append(");");

        return query.toString();
    }

    private void buildConstraintQuery(EntityNode<?> entityNode, StringBuilder query) {
        String constraintQuery = entityNode.getFields(Transient.class).stream()
                .map(this::buildConstraintQuery)
                .filter(q -> !q.isBlank())
                .collect(Collectors.joining(", "));

        System.out.println("constraintQuery = " + constraintQuery);
        query.append(constraintQuery);
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

    private void buildColumnQuery(EntityNode<?> entityNode, StringBuilder query) {
        for (FieldNode fieldNode : entityNode.getFields(Transient.class)) {
            query.append(buildColumnQuery(fieldNode)).append(", ");
        }
    }

    private String buildColumnQuery(FieldNode fieldNode) {
        return columnQuerySuppliers.stream().filter(supplier -> supplier.supported(fieldNode))
                .map(supplier -> supplier.supply(fieldNode).trim()).collect(Collectors.joining(" "));
    }

    private <T> String buildTableQuery(EntityNode<T> entityNode) {
        String tableName = getTableName(entityNode);

        return "CREATE TABLE " + nameConverter.convert(tableName);
    }

    @Override
    public String buildDropTableQuery(EntityNode<?> entityNode) {
        String tableName = getTableName(entityNode);

        return "DROP TABLE " + nameConverter.convert(tableName);
    }

    private static <T> String getTableName(EntityNode<T> entityNode) {
        Class<T> entityClass = entityNode.getEntityClass();
        String tableName = entityClass.getSimpleName();

        if (entityClass.isAnnotationPresent(Table.class)) {
            tableName = entityClass.getAnnotation(Table.class).name();
        }
        return tableName;
    }
}
