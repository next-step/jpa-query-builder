package builder.h2;

import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class H2QueryBuilderDDLTest {

    @DisplayName("create 쿼리 생성시 Entity어노테이션이 존재하지 않으면 예외를 발생시킨다.")
    @Test
    void buildCreateQueryNotExistEntityThrowException() {
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
        assertThatThrownBy(() -> h2QueryBuilderDDL.buildCreateQuery(Person.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("@Entity 어노테이션이 존재하지 않습니다.");
    }

    @DisplayName("create쿼리를 생성 시 @Id가 지정된 변수를 PK로 가져간다.")
    @Test
    void confirmIdAnnotationTest() {
        @Entity
        class Person {

            @Id
            private Long id;

        }
        H2QueryBuilderDDL h2QueryBuilderDDL = new H2QueryBuilderDDL();
        String createQuery = h2QueryBuilderDDL.buildCreateQuery(Person.class);
        assertThat(createQuery).isEqualTo(
                "CREATE TABLE Person (id BIGINT NOT NULL PRIMARY KEY);"
        );
    }

    @DisplayName("create쿼리를 생성 시 @Column이 지정되어있지 않으면 변수명을 컬럼명으로 생성한다.")
    @Test
    void notExistColumnAnnotationTest() {
        @Entity
        class Person {

            @Id
            private Long id;

            private String name;

            private Integer age;

            private String email;

        }
        H2QueryBuilderDDL h2QueryBuilderDDL = new H2QueryBuilderDDL();
        assertThat(h2QueryBuilderDDL.buildCreateQuery(Person.class)).isEqualTo(
                "CREATE TABLE Person (id BIGINT NOT NULL PRIMARY KEY, name VARCHAR(255), age INTEGER, email VARCHAR(255));"
        );
    }

    @DisplayName("create쿼리를 생성 시 @Column이 지정되어 있으면 확인하여 생성한다.")
    @Test
    void existColumnAnnotationTest() {
        @Entity
        class Person {

            @Id
            private Long id;

            @Column(name = "nick_name")
            private String name;

            @Column(name = "old")
            private Integer age;

            @Column(nullable = false)
            private String email;

        }
        H2QueryBuilderDDL h2QueryBuilderDDL = new H2QueryBuilderDDL();
        assertThat(h2QueryBuilderDDL.buildCreateQuery(Person.class)).isEqualTo(
                "CREATE TABLE Person (id BIGINT NOT NULL PRIMARY KEY, nick_name VARCHAR(255), old INTEGER, email VARCHAR(255) NOT NULL);"
        );
    }

    @DisplayName("create쿼리를 생성 시 @Id가 2개 이상이면 예외가 발생한다.")
    @Test
    void existIdAnnotationOverTwoTest() {
        @Entity
        class Person {

            @Id
            private Long id;

            @Id
            private String name;

        }
        H2QueryBuilderDDL h2QueryBuilderDDL = new H2QueryBuilderDDL();
        assertThatThrownBy(() -> h2QueryBuilderDDL.buildCreateQuery(Person.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("@Id 어노테이션은 한개를 초과할수 없습니다.");
    }

    @DisplayName("create쿼리를 생성 시 @Table이 지정되어있다면 테이블명을 가져온다.")
    @Test
    void existTableAnnotationOverTwoTest() {
        @Table(name = "users")
        @Entity
        class Person {

            @Id
            private Long id;

            @Column(name = "nick_name")
            private String name;

            @Column(name = "old")
            private Integer age;

            @Column(nullable = false)
            private String email;

        }
        H2QueryBuilderDDL h2QueryBuilderDDL = new H2QueryBuilderDDL();
        assertThat(h2QueryBuilderDDL.buildCreateQuery(Person.class)).isEqualTo(
                "CREATE TABLE users (id BIGINT NOT NULL PRIMARY KEY, nick_name VARCHAR(255), old INTEGER, email VARCHAR(255) NOT NULL);"
        );
    }

    @DisplayName("create쿼리를 생성 시 @GeneratedValue가 지정되어있다면 테이블명을 가져온다.")
    @Test
    void existGeneratedValueAnnotationOverTwoTest() {
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
        assertThat(h2QueryBuilderDDL.buildCreateQuery(Person.class)).isEqualTo(
                "CREATE TABLE users (id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, nick_name VARCHAR(255), old INTEGER, email VARCHAR(255) NOT NULL);"
        );
    }

    @DisplayName("create쿼리를 생성 시 @Transient가 지정되어있다면 컬럼을 생성하지 않는다.")
    @Test
    void existTransientAnnotationOverTwoTest() {
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
        H2QueryBuilderDDL h2QueryBuilderDDL = new H2QueryBuilderDDL();
        assertThat(h2QueryBuilderDDL.buildCreateQuery(Person.class)).isEqualTo(
                "CREATE TABLE users (id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, nick_name VARCHAR(255), old INTEGER, email VARCHAR(255) NOT NULL);"
        );
    }

    @DisplayName("drop쿼리를 생성한다.")
    @Test
    void createDropQueryTest() {
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
        H2QueryBuilderDDL h2QueryBuilderDDL = new H2QueryBuilderDDL();
        assertThat(h2QueryBuilderDDL.buildDropQuery(Person.class)).isEqualTo(
                "DROP TABLE users;"
        );
    }

    @DisplayName("drop쿼리를 생성할시 @Entity가 없다면 예외를 발생시킨다.")
    @Test
    void createDropQueryNotExistEntityThrowExceptionTest() {
        @Table(name = "users")
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
        H2QueryBuilderDDL h2QueryBuilderDDL = new H2QueryBuilderDDL();
        assertThatThrownBy(() -> h2QueryBuilderDDL.buildDropQuery(Person.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("@Entity 어노테이션이 존재하지 않습니다.");
    }

}
