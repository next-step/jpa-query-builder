package persistence.sql.ddl;

import jakarta.persistence.*;
import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.extractor.ColumnExtractor;
import persistence.sql.ddl.extractor.TableExtractor;

import java.util.List;
import java.util.stream.Collectors;

public class DDLQueryGenerator {
    private final Dialect dialect;

    DDLQueryGenerator(Dialect dialect) {
        this.dialect = dialect;
    }

    public String generateDropTableQuery(final Class<?> entityClazz) {
        checkIsEntity(entityClazz);

        final String tableName = new TableExtractor(entityClazz).getTableName();
        return String.format("DROP TABLE %s", tableName);
    }

    public String generateCreateQuery(final Class<?> entityClazz) {
        checkIsEntity(entityClazz);
        List<ColumnExtractor> columnExtractors = ColumnExtractor.from(entityClazz, dialect);

        final String tableNameClause = new TableExtractor(entityClazz).getTableName();
        final String columnClause = getColumnClause(columnExtractors);
        final String keyClause = getKeyClause(columnExtractors);

        return String.format("CREATE TABLE %s (%s, %s)", tableNameClause, columnClause, keyClause);
    }

    private String getColumnClause(List<ColumnExtractor> columnExtractors) {
        return columnExtractors
                .stream()
                .map(this::getColumnString)
                .collect(Collectors.joining(", "));
    }

    private String getColumnString(ColumnExtractor extractor) {
        String result = String.format("%s %s", extractor.getName(), extractor.getColumnType());
        String generationType = extractor.getGenerationType();
        if (generationType != null) {
            result += String.format(" %s", generationType);
        }
        return result;
    }

    private String getKeyClause(List<ColumnExtractor> columnExtractors) {
        if(columnExtractors.stream().noneMatch(ColumnExtractor::isPrimaryKey)) {
            throw new IdAnnotationMissingException();
        }

        return columnExtractors.stream()
                .filter(extractor -> extractor.getKeyType() != null)
                .map(this::getKeyString)
                .collect(Collectors.joining(" ,"));
    }

    private String getKeyString(ColumnExtractor extractor) {
        return String.format("%s KEY (%s)", extractor.getKeyType(), extractor.getName());
    }

    private void checkIsEntity(Class<?> entityClazz) {
        if (!entityClazz.isAnnotationPresent(Entity.class)) {
            throw new AnnotationMissingException("Entity 어노테이션이 없습니다.");
        }
    }
}
