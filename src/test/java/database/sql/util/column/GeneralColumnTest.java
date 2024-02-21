package database.sql.util.column;

import database.sql.util.type.MySQLTypeConverter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class GeneralColumnTest {
    private final MySQLTypeConverter typeConverter = new MySQLTypeConverter();

    private static List<Arguments> sampleGeneralColumns() {
        return List.of(
                arguments(new GeneralColumn("abc", Long.class, null, false), "abc BIGINT NOT NULL"),
                arguments(new GeneralColumn("email", String.class, 42, true), "email VARCHAR(42) NULL"),
                arguments(new GeneralColumn("old", Integer.class, 42, false), "old INT NOT NULL")
        );
    }

    @ParameterizedTest
    @MethodSource("sampleGeneralColumns")
    void toColumnDefinition(Column column, String expected) {
        String columnDefinition = column.toColumnDefinition(typeConverter);
        assertThat(columnDefinition).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("sampleGeneralColumns")
    void isPrimaryKeyColumn(Column column) {
        assertThat(column.isPrimaryKeyField()).isFalse();
    }
}
