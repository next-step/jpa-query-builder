package persistence.sql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.sql.query.QueryBuilder;
import persistence.sql.ddl.targetentity.requirement1.Person;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TC 보충
public class EntityMetaServiceTest {

    private EntityMetaService entityMetaService;

    @BeforeEach
    void setUp() {
        entityMetaService = new EntityMetaService(new QueryBuilder());
    }

    @Test
    void Person_클래스_정보를_통해_create_쿼리_생성_1() {
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
    void Person_클래스_정보를_통해_create_쿼리_생성_2() {
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
    void Person_클래스_정보를_통해_create_쿼리_생성_3() {
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
    void Person_클래스_정보를_통해_drop_쿼리_생성_4() {
        // when
        String result = entityMetaService.generateDropTableQuery(persistence.sql.ddl.targetentity.requirement3.Person.class);

        // then
        assertThat(result).isEqualTo("drop table users");
    }

    @Test
    void Person_클래스_정보를_통해_insert_쿼리_생성() {
        // given
        List<Object> values = List.of(1, "유인근", 29, "keun0390@naver.com");

        // when
        String result = entityMetaService.generateInsertQuery(persistence.sql.dml.targetentity.Person.class, values);

        // then
        assertThat(result).isEqualTo("insert into users (id, nick_name, old, email) values (1, '유인근', 29, 'keun0390@naver.com')");
    }
}
