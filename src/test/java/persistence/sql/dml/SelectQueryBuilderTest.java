package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.Person;
import persistence.sql.column.Columns;
import persistence.sql.column.TableColumn;
import persistence.sql.dialect.Database;

import static org.assertj.core.api.Assertions.assertThat;

class SelectQueryBuilderTest {

    @DisplayName("Person 객체를 select one 쿼리로 변환한다.")
    @Test
    void buildFindQuery() {

        //given
        TableColumn tableColumn = TableColumn.from(Person.class, Database.MYSQL);
        Person person = new Person("username", 50, "test@test.com", 1);
        SelectQueryBuilder queryBuilder = new SelectQueryBuilder(tableColumn);

        //when
        String selectOneQuery = queryBuilder.build(person).findById(1L);

        //then
        assertThat(selectOneQuery).isEqualTo("select id, nick_name, old, email from users where id = 1");

    }
}
