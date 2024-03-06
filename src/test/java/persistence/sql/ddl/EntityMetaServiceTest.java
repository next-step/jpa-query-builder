package persistence.sql.ddl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.query.QueryBuilder;
import persistence.sql.ddl.targetentity.requirement1.Person;

import static org.assertj.core.api.Assertions.assertThat;

public class EntityMetaServiceTest {

    private EntityMetaService entityMetaService;

    @BeforeEach
    void setUp() {
        entityMetaService = new EntityMetaService(new QueryBuilder());
    }

    @Test
    void 요구사항_1_Person_클래스_정보를_통해_create_쿼리_생성() {
        // when
        String result = entityMetaService.generateCreateTableQuery(Person.class);

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
        String result = entityMetaService.generateCreateTableQuery(persistence.sql.ddl.targetentity.requirement2.Person.class);

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
        String result = entityMetaService.generateCreateTableQuery(persistence.sql.ddl.targetentity.requirement3.Person.class);

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
        String result = entityMetaService.generateDropTableQuery(persistence.sql.ddl.targetentity.requirement3.Person.class);

        // then
        assertThat(result).isEqualTo("drop table users");
    }
}
