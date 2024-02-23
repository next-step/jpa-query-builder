package persistence.sql.dml;

import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.extractor.ColumnExtractor;
import persistence.sql.extractor.TableExtractor;

import java.util.List;
import java.util.stream.Collectors;

public class DMLQueryGenerator {
    private final Dialect dialect;
    private final Class<?> clazz;
    private final List<ColumnExtractor> columnExtractors;

    public DMLQueryGenerator(Class<?> clazz, Dialect dialect) {
        this.dialect = dialect;
        this.clazz = clazz;
        this.columnExtractors = ColumnExtractor.from(clazz)
                .stream()
                .filter(columnExtractor -> !columnExtractor.isPrimaryKey())
                .collect(Collectors.toList());
    }

    public String generateInsertQuery(Object entity) {
        return String.format(
                "insert into %s (%s) values (%s)",
                new TableExtractor(clazz).getName(),
                columnsClause(),
                valueClause(entity)
        );
    }

    private String columnsClause() {
        return columnExtractors.stream()
                .map(ColumnExtractor::getName)
                .collect(Collectors.joining(", "));
    }

    private String valueClause(Object entity) {
        return columnExtractors.stream()
                .map(columnExtractor -> columnExtractor.getValue(entity).toString())
                .collect(Collectors.joining(", "));
    }
}
