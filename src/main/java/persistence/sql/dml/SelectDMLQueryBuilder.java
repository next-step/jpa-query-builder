package persistence.sql.dml;

import persistence.sql.dbms.Dialect;
import persistence.sql.dml.clause.WhereClause;
import persistence.sql.dml.clause.operator.WhereClauseSQLBuilder;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SelectDMLQueryBuilder<E> extends DMLQueryBuilder<E> {
    private static final String ALL_SELECT_COLUMNS = "*";

    private Set<String> selectClause;
    private WhereClause whereClause;

    public SelectDMLQueryBuilder(Dialect dialect, Class<E> entityClass) {
        super(dialect, entityClass);
    }

    public SelectDMLQueryBuilder<E> select(String... selectColumnNames) {
        this.selectClause = Arrays.stream(selectColumnNames)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return this;
    }

    public SelectDMLQueryBuilder<E> where(WhereClause whereClause) {
        this.whereClause = whereClause;
        return this;
    }


    @Override
    public String build() {
        return String.format("SELECT %s \n" +
                        " FROM %s \n " +
                        " %s" +
                        ";",
                createSelectClause(),
                createTableNameDefinition(),
                createWhereClause()
        );
    }

    private String createSelectClause() {
        if (selectClause == null || selectClause.isEmpty()) {
            return ALL_SELECT_COLUMNS;
        }

        return String.join(", ", selectClause);
    }

    private String createWhereClause() {
        if (whereClause == null) {
            return "";
        }

        return new WhereClauseSQLBuilder(whereClause).build();
    }
}
