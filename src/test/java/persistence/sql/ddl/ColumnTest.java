package persistence.sql.ddl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.Car;
import study.ReflectionTest;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ColumnTest {

    private static final Logger logger = LoggerFactory.getLogger(ColumnTest.class);

    @Test
    @DisplayName("Person Class로 Column 생성 : id")
    void createColumnClassWithPersonClass_id() throws Exception {
        Column idColumn = new Column(Person.class.getDeclaredField("id"));
        logger.info("{} 의 타입은 {}, primary 는 {}", idColumn.getName(), idColumn.getColumnType().getJavaType(), idColumn.isPrimary());
        assertAll(
                () -> assertThat(idColumn.getColumnType()).isEqualTo(ColumnType.BIGINT),
                () -> assertThat(idColumn.getName()).isEqualTo("id"),
                () -> assertThat(idColumn.isPrimary()).isTrue()
        );
    }

    @Test
    @DisplayName("Person Class로 Column 생성 : name")
    void createColumnClassWithPersonClass_name() throws Exception {
        Column nameColumn = new Column(Person.class.getDeclaredField("name"));
        logger.info("{} 의 타입은 {}, primary 는 {}", nameColumn.getName(), nameColumn.getColumnType().getJavaType(), nameColumn.isPrimary());
        assertAll(
                () -> assertThat(nameColumn.getColumnType()).isEqualTo(ColumnType.VARCHAR),
                () -> assertThat(nameColumn.getName()).isEqualTo("name"),
                () -> assertThat(nameColumn.isPrimary()).isFalse()
        );
    }

    @Test
    @DisplayName("Person Class로 Column 생성 : age")
    void createColumnClassWithPersonClass_age() throws Exception {
        Column ageColumn = new Column(Person.class.getDeclaredField("age"));
        logger.info("{} 의 타입은 {}, primary 는 {}", ageColumn.getName(), ageColumn.getColumnType().getJavaType(), ageColumn.isPrimary());
        assertAll(
                () -> assertThat(ageColumn.getColumnType()).isEqualTo(ColumnType.INTEGER),
                () -> assertThat(ageColumn.getName()).isEqualTo("age"),
                () -> assertThat(ageColumn.isPrimary()).isFalse()
        );
    }
}
