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

class InsertQueryBuilderTest {

    private Person person;
    private Dialect dialect;

    @BeforeEach
    void setUp(){
        dialect = Database.MYSQL.createDialect();
        person = new Person("username", 50, "test@test.com", 1);
    }
    @DisplayName("Person 객체를 insert 쿼리로 변환한다.")
    @Test
    void testInsertDml() {
        //given
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(dialect);
        Person person = new Person("username", 50, "test@test.com", 1);

        //when
        String insert = insertQueryBuilder.build(person);

        //then
        assertThat(insert).isEqualTo("insert into users (nick_name, old, email) values ('username', 50, 'test@test.com')");
    }

    @DisplayName("Person 객체를 insert 쿼리로 변환한다. - username과 index만 입력")
    @Test
    void testInsertDmlWhenSpecificField() {
        //given
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(dialect);
        Person person = new Person("username", "email2@test.com", 12);

        //when
        String insert = insertQueryBuilder.build(person);

        //then
        assertThat(insert).isEqualTo("insert into users (nick_name, old, email) values ('username', null, 'email2@test.com')");
    }
}
