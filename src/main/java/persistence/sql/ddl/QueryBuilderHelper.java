package persistence.sql.ddl;

import persistence.sql.ddl.node.EntityNode;

import java.util.stream.Collectors;

public abstract class QueryBuilderHelper implements QueryBuilder {

    public static final String DELIMITER_STR = ", ";

    public String createTableQuery(EntityNode<?> entityNode) {
        StringBuilder query = new StringBuilder();

        query.append(buildCreateTableQuery(entityNode)).append(" (");

        String columnQuery = entityNode.getFields().stream()
                .map(this::buildColumnQuery)
                .collect(Collectors.joining(DELIMITER_STR));
        query.append(columnQuery);

        if (entityNode.existsConstraint()) {
            String constraintQuery = entityNode.getFields().stream()
                    .map(this::buildConstraintQuery)
                    .collect(Collectors.joining(DELIMITER_STR));
            query.append(constraintQuery);
        }

        query.append(DELIMITER_STR).append(buildPrimaryKeyQuery(entityNode.getFields())).append(");");

        return query.toString();
    }

    protected String camelToSnake(String camel) {
        return camel.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }
}
