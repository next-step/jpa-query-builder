package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.dialect.h2.H2Dialect;
import persistence.sql.entity.EntityData;

import static org.assertj.core.api.Assertions.assertThat;

class InsertQueryBuilderTest {

    @DisplayName("엔티티에 알맞는 Insert 쿼리를 생성한다.")
    @Test
    void insertQueryTest() {
        Person entity = new Person("test1", 10, "test1@gmail.com", 0);
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(new H2Dialect());

        String actualQuery = insertQueryBuilder.generateQuery(new EntityData(entity.getClass()), entity);

        assertThat(actualQuery).isEqualTo("insert into users (id, nick_name, old, email) values (default, 'test1', 10, 'test1@gmail.com')");
    }

}
