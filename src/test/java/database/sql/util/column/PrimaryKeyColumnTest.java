package database.sql.util.column;

import database.sql.util.type.MySQLTypeConverter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PrimaryKeyColumnTest {
    private final MySQLTypeConverter typeConverter = new MySQLTypeConverter();

    private static Stream<Arguments> sampleGeneralColumns() {
        return Stream.of(
                arguments(new PrimaryKeyColumn("id1", Long.class, null, false), "id1 BIGINT PRIMARY KEY"),
                arguments(new PrimaryKeyColumn("id2", String.class, 42, false), "id2 VARCHAR(42) PRIMARY KEY"),
                arguments(new PrimaryKeyColumn("id3", Integer.class, 42, true), "id3 INT AUTO_INCREMENT PRIMARY KEY")
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
        assertThat(column.isPrimaryKeyField()).isTrue();
    }
}
