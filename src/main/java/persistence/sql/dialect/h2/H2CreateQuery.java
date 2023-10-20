package persistence.sql.dialect.h2;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import persistence.sql.entity.EntityColumn;
import persistence.sql.entity.EntityData;

import java.util.List;

/**
 * H2 CreateQuery 생성 클래스
 */
public class H2CreateQuery implements H2Query {

    public String generateQuery(EntityData entityData) {
        StringBuilder query = new StringBuilder();
        // "create table "
        query.append(CREATE_QUERY);

        // 테이블 명
        appendTableName(entityData, query);

        query.append(OPEN_PARENTHESIS);
        // 컬럼 명
        appendColumns(entityData, query);
        // 기본키
        appendPrimaryKey(entityData, query);
        query.append(CLOSE_PARENTHESIS);

        return query.toString();
    }

    private void appendTableName(EntityData entityData, StringBuilder query) {
        query.append(entityData.getTableName());
        query.append(SPACE);
    }

    private void appendColumns(EntityData entityData, StringBuilder query) {
        List<EntityColumn> entityColumns = entityData.getEntityColumns().getEntityColumnList();
        for (EntityColumn entityColumn : entityColumns) {
            query.append(getColumnPartInCreateQuery(entityColumn));
        }
    }

    private void appendPrimaryKey(EntityData entityData, StringBuilder query) {
        query.append(getPrimaryKeyInCreateQuery(entityData.getPrimaryKey()));
    }

    /**
     * Create 문의 컬럼 부분을 생성
     */
    private String getColumnPartInCreateQuery(EntityColumn entityColumn) {
        StringBuilder columnQueryPart = new StringBuilder();

        // 컬럼명
        appendColumnName(entityColumn, columnQueryPart);

        // 컬럼타입
        H2ColumnType h2ColumnType = H2ColumnType.typeMap.get(entityColumn.getType());
        appendColumnType(entityColumn, h2ColumnType, columnQueryPart);

        // id인 경우 생성 방법 명시
        appendIdPropertiesIfId(entityColumn, columnQueryPart);

        // not null (필요시)
        appendNotNullIfNeed(entityColumn, columnQueryPart);

        // 끝 처리 (comma & space)
        appendForNext(columnQueryPart);

        return columnQueryPart.toString();
    }

    private void appendColumnName(EntityColumn entityColumn, StringBuilder columnQueryPart) {
        columnQueryPart.append(entityColumn.getColumnName());
        columnQueryPart.append(SPACE);
    }

    private void appendColumnType(EntityColumn entityColumn, H2ColumnType h2ColumnType, StringBuilder columnQueryPart) {
        columnQueryPart.append(h2ColumnType.dbType);
        if (H2ColumnType.STRING.equals(h2ColumnType)) {
            columnQueryPart.append(H2ColumnTypeProperties.getVarcharLength(entityColumn.getField()));
        }
    }

    private void appendIdPropertiesIfId(EntityColumn entityColumn, StringBuilder columnQueryPart) {
        if (entityColumn.isId() && entityColumn.getField().isAnnotationPresent(GeneratedValue.class)) {
            columnQueryPart.append(SPACE);
            columnQueryPart.append(GENERATED_BY);
            columnQueryPart.append(entityColumn.getField().getAnnotation(GeneratedValue.class).strategy().name().toLowerCase());
        }
    }

    private void appendNotNullIfNeed(EntityColumn entityColumn, StringBuilder columnQueryPart) {
        if (doesNeedNotNull(entityColumn)) {
            columnQueryPart.append(SPACE);
            columnQueryPart.append(NOT_NULL);
        }
    }

    private boolean doesNeedNotNull(EntityColumn entityColumn) {
        return checkId(entityColumn) || checkColumn(entityColumn);
    }

    private boolean checkId(EntityColumn entityColumn) {
        return entityColumn.isId() && !entityColumn.getField().isAnnotationPresent(GeneratedValue.class);
    }

    private boolean checkColumn(EntityColumn entityColumn) {
        return entityColumn.getField().isAnnotationPresent(Column.class) && !entityColumn.getField().getAnnotation(Column.class).nullable();
    }

    private void appendForNext(StringBuilder columnQueryPart) {
        columnQueryPart.append(COMMA);
        columnQueryPart.append(SPACE);
    }

    /**
     * Create 문의 Primary Key 부분 생성
     */
    private String getPrimaryKeyInCreateQuery(EntityColumn primaryKey) {
        StringBuilder primaryKeyPart = new StringBuilder();
        primaryKeyPart.append(PRIMARY_KEY);
        primaryKeyPart.append(OPEN_PARENTHESIS);
        primaryKeyPart.append(primaryKey.getColumnName());
        primaryKeyPart.append(CLOSE_PARENTHESIS);
        return primaryKeyPart.toString();
    }

}
