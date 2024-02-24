package database.sql.util.column;

import database.sql.util.type.MySQLTypeConverter;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;

class PrimaryKeyEntityColumnTest {
    private final MySQLTypeConverter typeConverter = new MySQLTypeConverter();

    private static List<Arguments> sampleGeneralColumns() {
        return List.of(
//                arguments(new PrimaryKeyEntityColumn(field, "id1", Long.class, null, false), "id1 BIGINT PRIMARY KEY"),
//                arguments(new PrimaryKeyEntityColumn(field, "id2", String.class, 42, false), "id2 VARCHAR(42) PRIMARY KEY"),
//                arguments(new PrimaryKeyEntityColumn(field, "id3", Integer.class, 42, true), "id3 INT AUTO_INCREMENT PRIMARY KEY")
        );
    }

//    @ParameterizedTest
//    @MethodSource("sampleGeneralColumns")
//    void toColumnDefinition(EntityColumn entityColumn, String expected) {
//        String columnDefinition = entityColumn.toColumnDefinition(typeConverter);
//        assertThat(columnDefinition).isEqualTo(expected);
//    }
//
//    @ParameterizedTest
//    @MethodSource("sampleGeneralColumns")
//    void isPrimaryKeyColumn(EntityColumn entityColumn) {
//        assertThat(entityColumn.isPrimaryKeyField()).isTrue();
//    }
}
