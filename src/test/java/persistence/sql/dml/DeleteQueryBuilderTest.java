package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.Person;
import persistence.sql.column.Column;
import persistence.sql.column.Columns;
import persistence.sql.column.IdColumn;
import persistence.sql.column.TableColumn;
import persistence.sql.dialect.Database;
import persistence.sql.dialect.Dialect;

import static org.assertj.core.api.Assertions.assertThat;

class DeleteQueryBuilderTest {

    @Test
    @DisplayName("Person 객체를 delete 쿼리로 변환한다.")
    void testDeleteDml() {
        //given
        Dialect dialect = Database.MYSQL.createDialect();
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(dialect);
        Person person = new Person("username", 50, "test@test.com", 1);

        //when
        String query = deleteQueryBuilder.build(person).deleteById(1L);

        //then
        assertThat(query).isEqualTo("delete from users where id = 1");
    }

}
