package persistence.sql.ddl.dialect.h2;

import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.entity.EntityColumn;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * H2 DB 쿼리 생성
 */
public class H2Dialect extends Dialect {

    private static final String NOT_NULL = "not null";
    private static final String PRIMARY_KEY = "primary key";

    public H2Dialect() {
        super("create table");
    }

    /**
     * Java Type과 H2 DB 타입을 매핑하기 위한 Map
     */
    private static final Map<Class<?>, H2ColumnType> typeMap = Arrays.stream(H2ColumnType.values())
            .collect(Collectors.toMap(
                    columnTypeEnum -> columnTypeEnum.fieldType,
                    Function.identity()));

    /**
     * Create 문의 컬럼 부분을 생성
     */
    @Override
    public String getColumnPartInCreateQuery(EntityColumn entityColumn) {
        StringBuilder columnQueryPart = new StringBuilder();

        // 컬럼명
        appendColumnName(entityColumn, columnQueryPart);

        // 컬럼타입
        H2ColumnType h2ColumnType = typeMap.get(entityColumn.getType());
        appendColumnType(entityColumn, h2ColumnType, columnQueryPart);

        // not null (필요시)
        appendNotNullIfNeed(entityColumn, columnQueryPart);

        // 끝 처리 (comma & space)
        appendForNext(columnQueryPart);

        return columnQueryPart.toString();
    }

    private void appendColumnName(EntityColumn entityColumn, StringBuilder columnQueryPart) {
        columnQueryPart.append(entityColumn.getName());
        columnQueryPart.append(this.SPACE);
    }

    private void appendColumnType(EntityColumn entityColumn, H2ColumnType h2ColumnType, StringBuilder columnQueryPart) {
        columnQueryPart.append(h2ColumnType.dbType);
        if (H2ColumnType.STRING.equals(h2ColumnType)) {
            columnQueryPart.append(h2ColumnType.properties.apply(entityColumn.getField()));
        }
    }

    private void appendNotNullIfNeed(EntityColumn entityColumn, StringBuilder columnQueryPart) {
        if (entityColumn.isId()) {
            columnQueryPart.append(this.SPACE);
            columnQueryPart.append(NOT_NULL);
        }
    }

    private void appendForNext(StringBuilder columnQueryPart) {
        columnQueryPart.append(this.COMMA);
        columnQueryPart.append(this.SPACE);
    }

    /**
     * Create 문의 Primary Key 부분 생성
     */
    @Override
    public String getPrimaryKeyInCreateQuery(EntityColumn primaryKey) {
        StringBuilder primaryKeyPart = new StringBuilder();
        primaryKeyPart.append(PRIMARY_KEY);
        primaryKeyPart.append(this.SPACE);

        primaryKeyPart.append(this.OPEN_PARENTHESIS);
        primaryKeyPart.append(primaryKey.getName());
        primaryKeyPart.append(this.CLOSE_PARENTHESIS);
        return primaryKeyPart.toString();
    }

}
