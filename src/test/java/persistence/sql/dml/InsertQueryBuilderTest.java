package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.Person;
import persistence.sql.column.Columns;
import persistence.sql.column.TableColumn;
import persistence.sql.dialect.Database;

import static org.assertj.core.api.Assertions.assertThat;

class InsertQueryBuilderTest {

    @DisplayName("Person 객체를 insert 쿼리로 변환한다.")
    @Test
    void testInsertDml() {
        //given
        TableColumn tableColumn = TableColumn.from(Person.class, Database.MYSQL);

        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(tableColumn);
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
        TableColumn tableColumn = TableColumn.from(Person.class, Database.MYSQL);
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(tableColumn);
        Person person = new Person("username", "email2@test.com", 12);

        //when
        String insert = insertQueryBuilder.build(person);

        //then
        assertThat(insert).isEqualTo("insert into users (nick_name, old, email) values ('username', null, 'email2@test.com')");
    }
}
