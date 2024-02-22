package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.Person;
import persistence.sql.dialect.Database;

import static org.assertj.core.api.Assertions.assertThat;

class DeleteQueryBuilderTest {

    @Test
    @DisplayName("Person 객체를 delete 쿼리로 변환한다.")
    void testDeleteDml() {
        Person person = new Person("username", 50, "test@test.com", 1);
        DeleteQueryBuilder deleteQueryBuilder = DeleteQueryBuilder.generate(Person.class, Database.MYSQL);

        String query = deleteQueryBuilder.build().deleteById(1L);

        assertThat(query).isEqualTo("delete from users where id = 1");
    }

}
