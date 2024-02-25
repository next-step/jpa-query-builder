package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.Person;
import persistence.sql.column.TableColumn;
import persistence.sql.dialect.Database;

import static org.assertj.core.api.Assertions.assertThat;

class DropQueryBuilderTest {

    @DisplayName("Person 클래스의 DDL을 삭제한다.")
    @Test
    void dropDdl() {
        //given
        TableColumn tableColumn = new TableColumn(Person.class);

        //when
        String ddl = new DropQueryBuilder(tableColumn).build();

        //then
        assertThat("drop table users").isEqualTo(ddl);
    }
}
