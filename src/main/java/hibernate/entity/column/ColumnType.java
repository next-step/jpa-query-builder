package hibernate.entity.column;

import java.util.Arrays;

public enum ColumnType {

    BIG_INT("bigint", Long.class),
    INTEGER("integer", Integer.class),
    VAR_CHAR("varchar", String.class),
    ;

    private final String h2ColumnType;
    private final Class<?> javaColumnClass;

    ColumnType(final String h2ColumnType, final Class<?> javaColumnClass) {
        this.h2ColumnType = h2ColumnType;
        this.javaColumnClass = javaColumnClass;
    }

    public static ColumnType valueOf(final Class<?> javaColumnClass) {
        return Arrays.stream(values())
                .filter(columnType -> columnType.javaColumnClass == javaColumnClass)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("호환되는 컬럼을 찾을 수 없습니다."));
    }

    public String getH2ColumnType() {
        return h2ColumnType;
    }
}
