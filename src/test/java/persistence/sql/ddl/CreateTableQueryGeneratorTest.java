package persistence.sql.ddl;

import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.TypeMapper.H2TypeMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CreateTableQueryGeneratorTest {
    CreateTableQueryGenerator sut = new CreateTableQueryGenerator(new H2TypeMapper());

    @Test
    @DisplayName("요구사항1: 기본 테이블 생성 쿼리 생성 테스트")
    void testGenerateCreateQuery() {
        String expect = "CREATE TABLE person (id BIGINT, name VARCHAR, age INTEGER, PRIMARY KEY (id))";
        @Entity
        class Person {
            @Id
            private Long id;

            private String name;

            private Integer age;
        }

        String query = sut.generateCreateQuery(Person.class);

        assertThat(query.toLowerCase()).isEqualTo(expect.toLowerCase());
    }

    @Test
    @DisplayName("기본키 없으면 에러")
    void throwErrorWhenPrimaryKeyIsNotDefined() {
        @Entity
        class Test {
            private Long id;
        }

        assertThrows(IdAnnotationMissingException.class, () -> {
            sut.generateCreateQuery(Test.class);
        });
    }

    @Test
    @DisplayName("Entity 클래스가 아니면 에러")
    void throwErrorWhenClassIsNotForEntity() {
        class Test {
            @Id
            private Long id;
        }

        assertThrows(AnnotationMissingException.class, () -> {
            sut.generateCreateQuery(Test.class);
        });
    }
}