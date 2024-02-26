package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.Person;
import persistence.sql.column.Columns;
import persistence.sql.column.IdColumn;
import persistence.sql.column.TableColumn;
import persistence.sql.dialect.Database;
import persistence.sql.dialect.Dialect;

import static org.assertj.core.api.Assertions.assertThat;

class CreateQueryBuilderTest {

    @DisplayName("Person 클래스의 DDL을 생성한다.")
    @Test
    void createDdl() {
        //given
        Class<Person> personEntity = Person.class;
        TableColumn table = new TableColumn(personEntity);
        Dialect dialect = Database.MYSQL.createDialect();
        Columns columns = new Columns(personEntity.getDeclaredFields(), dialect);
        IdColumn idColumn = new IdColumn(personEntity.getDeclaredFields(), dialect);
        CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(table, columns, idColumn);

        //when
        String ddl = createQueryBuilder.build();

        //then
        assertThat(ddl).isEqualTo("create table users (id bigint auto_increment primary key, nick_name varchar(255), old integer, email varchar(255) not null)");
    }
}
