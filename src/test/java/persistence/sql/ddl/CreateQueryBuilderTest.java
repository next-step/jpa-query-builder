package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.Person;
import persistence.sql.column.Columns;
import persistence.sql.column.TableColumn;
import persistence.sql.dialect.Database;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class CreateQueryBuilderTest {

    @DisplayName("Person 클래스의 DDL을 생성한다.")
    @Test
    void createDdl() {
        //given
        TableColumn table = TableColumn.from(Person.class, Database.MYSQL);
        CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(table);

        //when
        String ddl = createQueryBuilder.build();

        //then
        assertThat(ddl).isEqualTo("create table users (id bigint auto_increment primary key, nick_name varchar(255), old integer, email varchar(255) not null)");
    }
}
