package persistence.sql.ddl;

import jakarta.persistence.*;
import persistence.sql.dialect.Dialect;
import persistence.sql.ddl.exception.AnnotationMissingException;
import persistence.sql.ddl.exception.IdAnnotationMissingException;
import persistence.sql.extractor.ColumnData;
import persistence.sql.extractor.ColumnExtractor;
import persistence.sql.extractor.TableData;
import persistence.sql.extractor.TableExtractor;

import java.util.List;
import java.util.stream.Collectors;

public class DDLQueryGenerator {
    private final Dialect dialect;
    private final Class<?> clazz;
    private final TableData tableData;
    private final List<ColumnData> columns;

    public DDLQueryGenerator(Dialect dialect, Class<?> clazz) {
        this.dialect = dialect;
        this.clazz = clazz;
        this.tableData = new TableExtractor(clazz).createTable();
        this.columns = new ColumnExtractor(clazz).createColumns();
    }

    public String generateDropTableQuery() {
        checkIsEntity(clazz);

        return String.format("DROP TABLE %s", tableData.getName());
    }

    public String generateCreateQuery() {
        checkIsEntity(clazz);

        final String tableNameClause = tableData.getName();
        final String columnClause = getColumnClause();
        final String keyClause = getKeyClause();

        return String.format("CREATE TABLE %s (%s, %s)", tableNameClause, columnClause, keyClause);
    }

    private String getColumnClause() {
        return columns
                .stream()
                .map(this::getColumnString)
                .collect(Collectors.joining(", "));
    }

    private String getColumnString(ColumnData columnData) {
        String result = String.format("%s %s", columnData.getName(), dialect.mapDataType(columnData.getType()));
        if(!columnData.isNullable()) {
            result += " NOT NULL";
        }
        if (columnData.hasGenerationType()) {
            result += String.format(" %s", dialect.mapGenerationType(columnData.getGenerationType()));
        }
        return result;
    }

    private String getKeyClause() {
        if(columns.stream().noneMatch(ColumnData::isPrimaryKey)) {
            throw new IdAnnotationMissingException();
        }

        return columns.stream()
                .filter(columnData -> columnData.getKeyType() != null)
                .map(this::getKeyString)
                .collect(Collectors.joining(" ,"));
    }

    private String getKeyString(ColumnData columnData) {
        return String.format("%s KEY (%s)", dialect.mapKeyType(columnData.getKeyType()), columnData.getName());
    }

    private void checkIsEntity(Class<?> entityClazz) {
        if (!entityClazz.isAnnotationPresent(Entity.class)) {
            throw new AnnotationMissingException("Entity 어노테이션이 없습니다.");
        }
    }
}
