package persistence.sql.dml;

import persistence.model.EntityColumn;
import persistence.model.EntityFactory;
import persistence.model.EntityTable;
import persistence.model.meta.Value;
import persistence.sql.dialect.Dialect;
import persistence.sql.dml.clause.Clause;
import persistence.sql.dml.clause.EqualClause;
import persistence.sql.dml.clause.FindOption;
import persistence.sql.dml.clause.FindOptionBuilder;

import java.util.*;

public class DmlQueryBuilder {
    private final Dialect dialect;

    private static final String INSERT_FORMAT = "INSERT INTO %s (%s) VALUES (%s);";

    private static final String DELETE_FORMAT = "DELETE FROM %s";

    private static final String SELECT_FORMAT = "SELECT %s FROM %s";

    private static final String SELECT_ALL = "*";

    public DmlQueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    public String buildInsertQuery(Object entityObject) {
        EntityTable table = EntityFactory.createPopulatedSchema(entityObject);

        List<EntityColumn> insertingColumns = table.getActiveColumns(table);
        List<String> insertingColumnNames = insertingColumns.stream()
                .map(EntityColumn::getName)
                .toList();
        List<Object> insertingValues = insertingColumns.stream()
                .map(EntityColumn::getValue)
                .map(Value::getValue)
                .toList();

        String tableName = table.getName();

        return String.format(
                INSERT_FORMAT,
                dialect.getIdentifierQuoted(tableName),
                dialect.getIdentifiersQuoted(insertingColumnNames),
                dialect.getValuesQuoted(insertingValues));
    }

    public String buildDeleteQuery(Class<?> entityClass, List<Map<String, Object>> equalFilters) {
        EntityTable table = EntityFactory.createEmptySchema(entityClass);

        FindOptionBuilder findOptionBuilder = getFindOptionBuilderWithWhere(
                new FindOptionBuilder(),
                table,
                equalFilters
        );

        String deleteSql = String.format(DELETE_FORMAT, dialect.getIdentifierQuoted(table.getName()));

        String whereClauseSql = findOptionBuilder.build().joinWhereClauses(dialect);
        if (whereClauseSql.isEmpty()) {
            return deleteSql;
        }
        return deleteSql + " " + whereClauseSql + ";";
    }

    public String buildSelectQuery(
            Class<?> entityClass
    ) {
        return buildSelectQuery(entityClass, new ArrayList<>(), new ArrayList<>());
    }

    public String buildSelectQuery(
            Class<?> entityClass,
            List<Map<String, Object>> equalFilters
    ) {
        return buildSelectQuery(entityClass, new ArrayList<>(), equalFilters);
    }

    public String buildSelectQuery(
            Class<?> entityClass,
            List<String> selectingColumns,
            List<Map<String, Object>> equalFilters
    ) {
        FindOptionBuilder findOptionBuilder = new FindOptionBuilder();
        EntityTable table = EntityFactory.createEmptySchema(entityClass);

        EntityColumn[] columns = selectingColumns.stream()
                .map(table::getColumn)
                .toArray(EntityColumn[]::new);

        findOptionBuilder = getFindOptionBuilderWithWhere(findOptionBuilder, table, equalFilters)
                .selectColumns(columns);

        return buildSelectQuery(entityClass, findOptionBuilder.build());
    }

    private String buildSelectQuery(Class<?> entityClass, FindOption findOption) {
        EntityTable table = EntityFactory.createEmptySchema(entityClass);

        List<String> selectingColumnNames = findOption.getSelectingColumns().stream()
                .map(EntityColumn::getName)
                .toList();

        String selectingColumnNamesJoined = selectingColumnNames.isEmpty()
                ? SELECT_ALL
                : dialect.getIdentifiersQuoted(selectingColumnNames);

        String query = String.format(
                SELECT_FORMAT,
                selectingColumnNamesJoined,
                dialect.getIdentifierQuoted(table.getName()));

        if (!findOption.getWhere().isEmpty()) {
            return query + " " + findOption.joinWhereClauses(dialect) + ";";
        }
        return query + ";";
    }

    private FindOptionBuilder getFindOptionBuilderWithWhere(
            FindOptionBuilder findOptionBuilder,
            EntityTable table,
            List<Map<String, Object>> equalFilters
    ) {
        if (equalFilters.isEmpty()) {
            return findOptionBuilder;
        }

        for (Map<String, Object> filter : equalFilters) {
            if (!equalFilters.isEmpty()) {
                Clause[] equalClauses = toEqualClauses(table, filter);
                findOptionBuilder.where(equalClauses);
            }
        }

        return findOptionBuilder;
    }

    private Clause[] toEqualClauses(EntityTable table, Map<String, Object> equalFilter) {
        List<Clause> currentGroup = new ArrayList<>();

        for (Map.Entry<String, Object> entry : equalFilter.entrySet()) {
            EntityColumn column = table.getColumn(entry.getKey());
            Clause clause = new EqualClause(column, entry.getValue());
            currentGroup.add(clause);
        }

        return currentGroup.toArray(Clause[]::new);
    }
}
