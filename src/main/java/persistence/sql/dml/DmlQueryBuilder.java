package persistence.sql.dml;

import persistence.model.EntityColumn;
import persistence.model.EntityFactory;
import persistence.model.EntityTable;
import persistence.model.exception.ColumnInvalidException;
import persistence.model.meta.Value;
import persistence.sql.dialect.Dialect;
import persistence.sql.dml.clause.Clause;
import persistence.sql.dml.clause.EqualClause;
import persistence.sql.dml.clause.FindOption;
import persistence.sql.dml.clause.FindOptionBuilder;

import java.util.*;
import java.util.stream.Collectors;

public class DmlQueryBuilder {
    private final Dialect dialect;

    private static final String INSERT_FORMAT = "INSERT INTO %s (%s) VALUES (%s);";

    private static final String DELETE_FORMAT = "DELETE FROM %s";

    private static final String SELECT_FORMAT = "SELECT %s FROM %s";

    private static final String SELECT_ALL = "*";

    private static final String UPDATE_FORMAT = "UPDATE %s";

    public DmlQueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    public String buildUpdateQuery(Object entityObject) {
        EntityTable table = EntityFactory.createPopulatedSchema(entityObject);

        if (!table.isPrimaryColumnsValueSet()) {
            throw new ColumnInvalidException("Primary Column is Required to Find Updating Record.");
        }

        String query = String.format(UPDATE_FORMAT, dialect.getIdentifierQuoted(table.getName()));

        String setQuery = "SET " + table.getColumns()
                .stream()
                .map(entityColumn -> String.format(
                        "%s = %s",
                        dialect.getIdentifierQuoted(entityColumn.getName()),
                        dialect.getValueQuoted(entityColumn.getValue()))
                )
                .collect(Collectors.joining(", "));

        FindOption findOption = getDefaultFindOptionWithPrimaryEqualClause(table);

        return query + " " + setQuery + " " + findOption.joinWhereClauses(dialect) + ";";
    }

    public String buildInsertQuery(Object entityObject) {
        EntityTable table = EntityFactory.createPopulatedSchema(entityObject);

        List<EntityColumn> insertingColumns = table.getActiveColumns(table);
        List<String> insertingColumnNames = insertingColumns.stream()
                .map(EntityColumn::getName)
                .toList();
        List<Object> insertingValues = insertingColumns.stream()
                .map(EntityColumn::getValue)
                .toList();

        String tableName = table.getName();

        return String.format(
                INSERT_FORMAT,
                dialect.getIdentifierQuoted(tableName),
                dialect.getIdentifiersQuoted(insertingColumnNames),
                dialect.getValuesQuoted(insertingValues));
    }

    public String buildDeleteQuery(Object entityObject) {
        EntityTable table = EntityFactory.createPopulatedSchema(entityObject);

        if (!table.isPrimaryColumnsValueSet()) {
            throw new ColumnInvalidException("column not initialized");
        }

        String deleteSql = String.format(DELETE_FORMAT, dialect.getIdentifierQuoted(table.getName()));

        FindOption findOption = getDefaultFindOptionWithPrimaryEqualClause(table);

        return deleteSql + " " + findOption.joinWhereClauses(dialect) + ";";
    }

    public String buildSelectByIdQuery(Class<?> entityClass, Long id) {
        EntityTable table = EntityFactory.createEmptySchema(entityClass);

        EntityColumn conditionColumn = table.getColumn("id");
        FindOption findOption = new FindOptionBuilder()
                .where(new EqualClause(conditionColumn, id))
                .build();

        return buildSelectQuery(entityClass, findOption);
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

    private FindOption getDefaultFindOptionWithPrimaryEqualClause(EntityTable table) {
        EntityColumn conditionColumn = table.getColumn("id");
        return new FindOptionBuilder()
                .where(new EqualClause(conditionColumn, conditionColumn.getValue()))
                .build();
    }
}
