package persistence.sql.dml;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.Person;
import persistence.sql.column.Columns;
import persistence.sql.column.IdColumn;
import persistence.sql.column.TableColumn;
import persistence.sql.dialect.Database;
import persistence.sql.dialect.Dialect;

import static org.assertj.core.api.Assertions.assertThat;

class SelectQueryBuilderTest {

    private Person person;
    private Dialect dialect;

    @BeforeEach
    void setUp() {
        dialect = Database.MYSQL.createDialect();
        person = new Person("username", 50, "test@test.com", 1);
    }

    @DisplayName("Person 객체를 select one 쿼리로 변환한다.")
    @Test
    void buildFindQuery() {

        //given
        SelectQueryBuilder queryBuilder = new SelectQueryBuilder(dialect);

        //when
        String selectOneQuery = queryBuilder.build(Person.class).findById(1L);

        //then
        assertThat(selectOneQuery).isEqualTo("select id, nick_name, old, email from users where id = 1");

    }

    @DisplayName("Person 객체를 select all 쿼리로 변환한다.")
    @Test
    void testSelectAllDml() {
        //given
        SelectQueryBuilder queryBuilder = new SelectQueryBuilder(dialect);

        //when
        String selectAll = queryBuilder.build(Person.class).findAll();

        //then
        assertThat(selectAll).isEqualTo("select id, nick_name, old, email from users");
    }
}
