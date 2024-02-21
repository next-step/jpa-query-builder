package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.Person;
import persistence.sql.dialect.Database;

import static org.assertj.core.api.Assertions.assertThat;

class DropDdlTest {

    @DisplayName("Person 클래스의 DDL을 삭제한다.")
    @Test
    void dropDdl() {
        QueryBuilder queryBuilder = new DropDdl();
        String ddl = queryBuilder.generate(Person.class, Database.MYSQL);

        assertThat("drop table users").isEqualTo(ddl);
    }
}
