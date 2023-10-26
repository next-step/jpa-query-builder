package persistence.sql.ddl;

import persistence.sql.DialectQueryBuilder;
import persistence.sql.SqlValueMapper;
import persistence.sql.dbms.Dialect;
import persistence.sql.entitymetadata.model.EntityColumn;
import persistence.sql.entitymetadata.model.EntityColumns;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CreateDDLQueryBuilder<E> extends DialectQueryBuilder<E> {

    private static final String INDENT = "    ";
    private static final String LINE_BREAK = "\n";

    public CreateDDLQueryBuilder(Dialect dialect, Class<E> entityClass) {
        super(dialect, entityClass);
    }

    @Override
    public String build() {
        String tableName = createTableNameDefinition();

        StringBuilder nativeQuery = new StringBuilder();
        nativeQuery
                .append("CREATE TABLE ")
                .append(tableName)
                .append(" (")
                .append(createColumnsQuery(entityTable.getColumns()))
                .append(LINE_BREAK)
                .append(")")
                .append(";");

        return nativeQuery.toString();
    }

    private String createPrimaryKeyQuery(EntityColumns<E> columns) {
        return String.format("PRIMARY KEY (%s)", columns.getIdColumn().getDbColumnName());
    }

    private String createColumnsQuery(EntityColumns<E> entityColumns) {
        List<String> columnQuerys = new ArrayList<>();

        for (EntityColumn<E, ?> entityColumn : entityColumns) {
            StringBuilder nativeQuery = new StringBuilder();

            nativeQuery.append(createColumnNameDefinition(entityColumn))
                    .append(" ")
                    .append(createColumnTypeDefinition(entityColumn))
                    .append(createColumnLengthDefinition(entityColumn))
                    .append(createColumnNullableDefinition(entityColumn));

            if (entityColumn.isIdColumn()) {
                nativeQuery.append(createIdColumnDefinition(entityColumn));
            }

            columnQuerys.add(nativeQuery.toString());
        }

        return LINE_BREAK + columnQuerys.stream()
                .map(s -> INDENT + s)
                .collect(Collectors.joining("," + LINE_BREAK));
    }

    private String createIdColumnDefinition(EntityColumn<E, ?> entityColumn) {
        if (!entityColumn.isIdColumn()) {
            throw new IllegalArgumentException("IdColumn이 아닙니다. column:" + entityColumn.getDbColumnName());
        }

        String idGenerationType = createIdGenerationTypeDefinition(entityColumn);

        return idGenerationType + " PRIMARY KEY";
    }

    private String createIdGenerationTypeDefinition(EntityColumn<E, ?> entityColumn) {
        switch (entityColumn.getIdGenerateType()) {
            case IDENTITY:
                return " AUTO_INCREMENT";
        }

        throw new UnsupportedOperationException("아직 지원하지 않는 IdGenerateType입니다. : " + entityColumn.getIdGenerateType());
    }

    private String createColumnLengthDefinition(EntityColumn<E, ?> entityColumn) {
        Integer length = entityColumn.getLength();

        if (Objects.isNull(length)) {
            return "";
        }

        return "(" + length + ")";
    }

    private String createColumnNullableDefinition(EntityColumn<E, ?> entityColumn) {
        return " " + (!entityColumn.isIdColumn() && entityColumn.isNullable() ? SqlValueMapper.NULL : SqlValueMapper.NOT_NULL);
    }
}
