package H2QueryBuilder;

import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class H2QueryBuilderDDLTest {
    @DisplayName("create쿼리 검증 1")
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

    @DisplayName("create쿼리 검증 2")
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

    @DisplayName("create쿼리 검증 3")
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

    @DisplayName("drop쿼리 검증 1")
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

}