package orm;

import jakarta.persistence.Column;

public record ColumnMeta(
        boolean nullable,
        int length
) {
    public static ColumnMeta from(Column column) {
        var nullable = column == null || column.nullable();
        var length = column == null ? 255 : column.length();

        return new ColumnMeta(
                nullable,
                length
        );
    }
}
