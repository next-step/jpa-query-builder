package persistence.sql.ddl;

import jakarta.persistence.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class QueryBuilderDDLTest {
    private static final Logger logger = LoggerFactory.getLogger(QueryBuilderDDLTest.class);

    private class TestClass { }

    @Entity
    public class DummyPerson {

        @Id
        private Long id;

        private String name;

        private Integer age;

    }

    @Entity
    public class DummyPerson2 {

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

    @Table(name = "users")
    @Entity
    public class DummyPerson3 {

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

    @Test
    @DisplayName("@Entity로 정의되지 않은 class 입력했을 때 - 오류 출력")
    void buildCreateDdl_error_notEntityValue() {
        QueryBuilderDDL queryBuilderDDL = QueryBuilderDDL.getInstance();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            queryBuilderDDL.buildCreateDdl(TestClass.class);
        });
    }

    @Test
    @DisplayName("Create Query Ddl 을 만들 class를 입력하고 compare")
    void createQuery_Compare_withDummyPerson1() {
        QueryBuilderDDL queryBuilderDDL = QueryBuilderDDL.getInstance();

        logger.info(queryBuilderDDL.buildCreateDdl(DummyPerson.class));
        assertThat(queryBuilderDDL.buildCreateDdl(DummyPerson.class))
                .isEqualTo("create table dummyperson (id bigint not null, name varchar(255), age integer, primary key (id));");
    }

    @Test
    @DisplayName("Create Query Ddl 을 만들 class를 입력하고 compare")
    void createQuery_Compare_withDummyPerson2() {
        QueryBuilderDDL queryBuilderDDL = QueryBuilderDDL.getInstance();

        logger.info(queryBuilderDDL.buildCreateDdl(DummyPerson2.class));
        assertThat(queryBuilderDDL.buildCreateDdl(DummyPerson2.class))
                .isEqualTo("create table dummyperson2 (id bigint not null auto_increment, nick_name varchar(255), old integer, email varchar(255) not null, primary key (id));");
    }

    @Test
    @DisplayName("Create Query Ddl 을 만들 class를 입력하고 compare")
    void createQuery_Compare_withDummyPerson3() {
        QueryBuilderDDL queryBuilderDDL = QueryBuilderDDL.getInstance();

        logger.info(queryBuilderDDL.buildCreateDdl(DummyPerson3.class));
        assertThat(queryBuilderDDL.buildCreateDdl(DummyPerson3.class))
                .isEqualTo("create table users (id bigint not null auto_increment, nick_name varchar(255), old integer, email varchar(255) not null, primary key (id));");
    }

    @Test
    @DisplayName("Drop Query Ddl 을 만들 class를 입력하고 compare")
    void dropQuery_Compare() {
        QueryBuilderDDL queryBuilderDDL = QueryBuilderDDL.getInstance();

        logger.info(queryBuilderDDL.buildDropDdl(Person.class));
        assertThat(queryBuilderDDL.buildDropDdl(Person.class))
                .isEqualTo("drop table users;");
    }

}
