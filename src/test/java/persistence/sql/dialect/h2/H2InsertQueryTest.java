package persistence.sql.dialect.h2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.entity.EntityData;

import static org.assertj.core.api.Assertions.assertThat;

class H2InsertQueryTest {

    @DisplayName("엔티티에 알맞는 insert 쿼리를 생성한다.")
    @Test
    void h2InsertQueryTest() {
        Person entity = new Person("test1", 10, "test1@gmail.com", 0);

        H2InsertQuery insertQuery = new H2InsertQuery();
        String actualQuery = insertQuery.generateQuery(new EntityData(entity.getClass()), entity);

        assertThat(actualQuery).isEqualTo("insert into users (id, nick_name, old, email) values (default, 'test1', 10, 'test1@gmail.com')");
    }

}
