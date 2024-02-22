package persistence.sql.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.constant.ColumnType;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[Unit] Column Create Test")
class ColumnTest {

    private final Class<?> testEntityClass = TestEntity.class;

    @DisplayName("필드가 @Id 어노테이션이 있을 경우 primary=true의 상태를 가지는 인스턴스를 생성한다.")
    @Test
    void create_column_with_primary_true() throws NoSuchFieldException {

        Field idField = testEntityClass.getDeclaredField("id");
        Column column = Column.create(idField, ColumnType.BIGINT);

        assertThat(column.isPrimary()).isTrue();
    }

    @DisplayName("필드가 @GeneratedValue 어노테이션이 있을 경우 generationType 값을 가지고 있는 인스턴스를 생성한다.")
    @Test
    void create_column_with_generation_type() throws NoSuchFieldException {
        Field idField = testEntityClass.getDeclaredField("id");
        Column column = Column.create(idField, ColumnType.BIGINT);

        assertThat(column.getGenerationType()).isEqualTo(GenerationType.IDENTITY);
    }

    @DisplayName("@Column 어노테이션에 name 옵션이 있을 경우 컬럼의 name을 옵션값으로 설정한 인스턴스를 생성한다.")
    @Test
    void create_column_with_name() throws NoSuchFieldException {

        Field ageField = testEntityClass.getDeclaredField("age");
        jakarta.persistence.Column annotation = ageField.getDeclaredAnnotation(jakarta.persistence.Column.class);
        Column column = Column.create(ageField, ColumnType.INTEGER);

        assertThat(column.getName()).isEqualTo(annotation.name());
    }

    @DisplayName("@Column 어노테이션에 name 옵션이 있을 경우 컬럼의 name을 옵션값으로 설정한 인스턴스를 생성한다.")
    @Test
    void create_column_with_nullable_true() throws NoSuchFieldException {

        Field ageField = testEntityClass.getDeclaredField("age");
        jakarta.persistence.Column annotation = ageField.getDeclaredAnnotation(jakarta.persistence.Column.class);
        Column column = Column.create(ageField, ColumnType.INTEGER);

        assertThat(column.isNullable()).isEqualTo(annotation.nullable());
    }

    @Entity
    class TestEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        @jakarta.persistence.Column(name = "old")
        private String age;

    }

}
