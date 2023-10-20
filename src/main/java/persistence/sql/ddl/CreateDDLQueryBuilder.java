package persistence.sql.ddl;

import persistence.sql.DbmsQueryBuilder;
import persistence.sql.dbms.DbmsStrategy;
import persistence.sql.entitymetadata.model.EntityColumn;
import persistence.sql.entitymetadata.model.EntityColumns;
import persistence.sql.entitymetadata.model.EntityTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CreateDDLQueryBuilder<E> extends DbmsQueryBuilder<E> {

    private static final String INDENT = "    ";
    private static final String NULL = "NULL";
    private static final String NOT_NULL = "NOT NULL";
    private static final String LINE_BREAK = "\n";

    public CreateDDLQueryBuilder(DbmsStrategy dbmsStrategy) {
        super(dbmsStrategy);
    }

    @Override
    public String build(EntityTable<E> e) {
        String tableName = createTableNameDefinition(e);

        StringBuilder nativeQuery = new StringBuilder();
        nativeQuery
                .append("CREATE TABLE ")
                .append(tableName)
                .append(" (")
                .append(createColumnsQuery(e.getColumns()))
                .append(LINE_BREAK)
                .append(")")
                .append(";");

        return nativeQuery.toString();
    }

    private String createPrimaryKeyQuery(EntityColumns<E> columns) {
        return String.format("PRIMARY KEY (%s)", columns.getIdColumn().getName());
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
            throw new IllegalArgumentException("IdColumn이 아닙니다. column:" + entityColumn.getName());
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
        return " " + (!entityColumn.isIdColumn() && entityColumn.isNullable() ? NULL : NOT_NULL);
    }
}
