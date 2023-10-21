package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.entity.EntityData;

import static org.assertj.core.api.Assertions.assertThat;

class DeleteQueryBuilderTest {

    @DisplayName("엔티티에 알맞는 delete 쿼리를 생성한다.")
    @Test
    void deleteQueryTest() {
        Person entity = new Person(1L, "test1", 10, "test1@gmail.com", 0);
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder();

        String actualQuery = deleteQueryBuilder.generateQuery(new EntityData(entity.getClass()), entity);

        assertThat(actualQuery).isEqualTo("delete from users where id = 1");
    }

}
