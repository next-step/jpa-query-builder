package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;

import static org.assertj.core.api.Assertions.assertThat;

public class EntityQueryBuilderTest {

    @Test
    @DisplayName("Person 엔터티 create 쿼리 만들기")
    public void createQueryTest() {
        EntityQueryBuilder entityQueryBuilder = new EntityQueryBuilder(Person.class);

        String query = entityQueryBuilder.create();

        assertThat(query).isEqualTo("CREATE TABLE users (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "nick_name VARCHAR(255)," +
                "old INT," +
                "email VARCHAR(255) NOT NULL);");
    }

    @Test
    @DisplayName("Person 엔터티 drop쿼리 만들기")
    public void dropQueryTest() {
        EntityQueryBuilder entityQueryBuilder = new EntityQueryBuilder(Person.class);

        String query = entityQueryBuilder.drop();

        assertThat(query).isEqualTo("DROP TABLE users;");
    }

}
