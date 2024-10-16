package H2QueryBuilder;

import common.ErrorCode;
import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class H2QueryBuilderDDLTest {
    @DisplayName("create 쿼리 검증 1")
    @Test
    void confirmIdAnnotationTest() {
        //given
        @Entity
        class Person {

            @Id
            private Long id;

        }
        H2QueryBuilderDDL h2QueryBuilderDDL = new H2QueryBuilderDDL();

        //when
        String createQuery = h2QueryBuilderDDL.create(Person.class);

        //then
        assertThat(createQuery).isEqualTo(
                "CREATE TABLE Person (id BIGINT NOT NULL PRIMARY KEY);"
        );
    }

    @DisplayName("create 쿼리 검증 2")
    @Test
    void notExistColumnAnnotationTest() {
        //given
        @Entity
        class Person {

            @Id
            private Long id;

            private String name;

            private Integer age;

            private String email;

        }
        H2QueryBuilderDDL h2QueryBuilderDDL = new H2QueryBuilderDDL();

        //when
        assertThat(h2QueryBuilderDDL.create(Person.class)).isEqualTo(
                "CREATE TABLE Person (id BIGINT NOT NULL PRIMARY KEY, name VARCHAR, age INT, email VARCHAR);"
        );
    }

    @DisplayName("create 쿼리 검증 3")
    @Test
    void existGeneratedValueAnnotationOverTwoTest() {
        //given
        @Table(name = "users")
        @Entity
        class Person {

            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private Long id;

            @Column(name = "nick_name")
            private String name;

            @Column(name = "old")
            private Integer age;

            @Column(nullable = false)
            private String email;

        }
        H2QueryBuilderDDL h2QueryBuilderDDL = new H2QueryBuilderDDL();

        //when, then
        assertThat(h2QueryBuilderDDL.create(Person.class)).isEqualTo(
                "CREATE TABLE users (id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, nick_name VARCHAR, old INT, email VARCHAR NOT NULL);"
        );
    }

    @DisplayName("drop 쿼리 검증 1")
    @Test
    void createDropQueryTest() {
        //given
        @Table(name = "users")
        @Entity
        class Person {

            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private Long id;

            @Column(name = "nick_name")
            private String name;

            @Column(name = "old")
            private Integer age;

            @Column(nullable = false)
            private String email;

        }
        H2QueryBuilderDDL h2QueryBuilderDDL = new H2QueryBuilderDDL();

        //when, then
        assertThat(h2QueryBuilderDDL.drop(Person.class)).isEqualTo(
                "DROP TABLE users;"
        );
    }

    @DisplayName("지원하지 않는 SQL 타입 Excpetion 검증")
    @Test
    void notSupplySqlTypeTest() {
        //given
        @Table(name = "users")
        @Entity
        class Person {
            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private Long id;

            @Column(name = "nick_name")
            private String name;

            @Column(name = "old")
            private Integer age;

            @Column(nullable = false)
            private String email;

            private Double cash;
        }

        H2QueryBuilderDDL h2QueryBuilderDDL = new H2QueryBuilderDDL();

        //when, then
        assertThatThrownBy(() -> h2QueryBuilderDDL.create(Person.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.NOT_ALLOWED_DATATYPE.getErrorMsg());
    }

}