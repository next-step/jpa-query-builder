package persistence.sql.ddl.query.builder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.query.generator.QueryGenerator;
import persistence.sql.ddl.targetentity.requirement1.Person;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryBuilderTest {

    private QueryBuilder queryBuilder;

    @BeforeEach
    void setUp() {
        queryBuilder = new QueryBuilder(new QueryGenerator());
    }

    @Test
    void 요구사항_1_Person_클래스_정보를_통해_create_쿼리_생성() {
        // when
        String result = queryBuilder.generateCreateTableQuery(Person.class);

        // then
        assertThat(result).isEqualTo("create table person (\n" +
                "    id BIGINT PRIMARY KEY,\n" +
                "    name VARCHAR,\n" +
                "    age BIGINT\n" +
                ")");
    }

    @Test
    void 요구사항_2_Person_클래스_정보를_통해_create_쿼리_생성() {
        // when
        String result = queryBuilder.generateCreateTableQuery(persistence.sql.ddl.targetentity.requirement2.Person.class);

        // then
        assertThat(result).isEqualTo("create table person (\n" +
                "    id BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
                "    nick_name VARCHAR,\n" +
                "    old BIGINT,\n" +
                "    email VARCHAR NOT NULL\n" +
                ")");
    }

    @Test
    void 요구사항_3_Person_클래스_정보를_통해_create_쿼리_생성() {
        // when
        String result = queryBuilder.generateCreateTableQuery(persistence.sql.ddl.targetentity.requirement3.Person.class);

        // then
        assertThat(result).isEqualTo("create table users (\n" +
                "    id BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
                "    nick_name VARCHAR,\n" +
                "    old BIGINT,\n" +
                "    email VARCHAR NOT NULL\n" +
                ")");
    }

    @Test
    void 요구사항_4_Person_클래스_정보를_통해_drop_쿼리_생성() {
        // when
        String result = queryBuilder.generateDropTableQuery(persistence.sql.ddl.targetentity.requirement3.Person.class);

        // then
        assertThat(result).isEqualTo("drop table users");
    }
}
