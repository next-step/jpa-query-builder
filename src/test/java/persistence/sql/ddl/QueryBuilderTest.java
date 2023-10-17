package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryBuilderTest {

    @Test
    @DisplayName("Person 엔터티 create 쿼리 만들기")
    public void createQueryTest() {
        QueryBuilder queryBuilder = new QueryBuilder();

        String query = queryBuilder.create(Person.class);

        assertThat(query).isEqualTo("CREATE TABLE users (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "nick_name VARCHAR(255)," +
                "old INT," +
                "email VARCHAR(255) NOT NULL);");
    }

    @Test
    @DisplayName("Person 엔터티 drop쿼리 만들기")
public void dropQueryTest() {
        QueryBuilder queryBuilder = new QueryBuilder();

        String query = queryBuilder.drop(Person.class);

        assertThat(query).isEqualTo("DROP TABLE users;");
    }

}
