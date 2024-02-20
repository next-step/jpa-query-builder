package persistence.sql.ddl;

import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.TypeMapper.H2TypeMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CreateTableQueryGeneratorTest {
    DDLQueryGenerator sut = new DDLQueryGenerator(new H2TypeMapper());

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
    @DisplayName("요구사항2: 추가된 정보 테이블 생성 쿼리 생성 테스트")
    void testGenerateCreateQuery2() {
        String expect = "CREATE TABLE person (id BIGINT auto_increment, nick_name VARCHAR, old INTEGER, email varchar not null, PRIMARY KEY (id))";
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

        String query = sut.generateCreateQuery(Person.class);

        assertThat(query.toLowerCase()).isEqualTo(expect.toLowerCase());
    }

    @Test
    @DisplayName("요구사항3: 추가된 정보 테이블 생성 쿼리 생성 테스트2")
    void testGenerateCreateQuery3() {
        String expect = "CREATE TABLE users (id BIGINT auto_increment, nick_name VARCHAR, old INTEGER, email varchar not null, PRIMARY KEY (id))";
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

            @Transient
            private Integer index;

        }

        String query = sut.generateCreateQuery(Person.class);

        assertThat(query.toLowerCase()).isEqualTo(expect.toLowerCase());
    }

    @Test
    @DisplayName("요구사항4: 정보를 바탕으로 drop 쿼리 만들어보기")
    void testDropQueryGenerate() {
        String expect = "drop table users";
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

            @Transient
            private Integer index;

        }

        String query = sut.dropTableQuery(Person.class);

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