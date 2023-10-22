package persistence.sql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class EntityColumnTest {

    private static final String TEST_NAME = "test_name";

    @Entity
    private static class ColumnTestClass {
        @Id
        private Long id;

        @Column
        private String withoutName;

        private String withoutColumn;

        @Column(name = TEST_NAME)
        private String withColumnAndName;
    }

    @DisplayName("@Column에 name이 명시되지 않은 경우, 필드이름을 컬럼 이름으로 한다.")
    @ParameterizedTest
    @CsvSource("withoutName, withoutColumn")
    void columnNameTestWithoutColumnName(String fieldName) throws Exception {
        Field field = ColumnTestClass.class.getDeclaredField(fieldName);
        EntityColumn entityColumn = new EntityColumn(field);
        assertThat(entityColumn.getColumnName()).isEqualTo(field.getName());
    }

    @DisplayName("@Column에 name이 명시된 경우, 해당 이름이 컬럼 이름이 된다.")
    @ParameterizedTest
    @CsvSource("withColumnAndName")
    void columnNameTestWithColumnName(String fieldName) throws Exception {
        Field field = ColumnTestClass.class.getDeclaredField(fieldName);
        EntityColumn entityColumn = new EntityColumn(field);
        assertThat(entityColumn.getColumnName()).isEqualTo(TEST_NAME);
    }

}
