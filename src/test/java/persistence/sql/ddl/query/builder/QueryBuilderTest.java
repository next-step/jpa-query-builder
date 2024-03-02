package persistence.sql.ddl.query.builder;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.targetentity.requirement1.Person;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryBuilderTest {

    @Test
    void 요구사항_1_Person_클래스_정보를_통해_create_쿼리_생성() {
        // when
        String result = QueryBuilder.generateCreateTableQuery(Person.class);

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
        String result = QueryBuilder.generateCreateTableQuery(persistence.sql.ddl.targetentity.requirement2.Person.class);

        // then
        assertThat(result).isEqualTo("create table person (\n" +
                "    id BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
                "    nick_name VARCHAR,\n" +
                "    old BIGINT,\n" +
                "    email VARCHAR NOT NULL\n" +
                ")");
    }
}
