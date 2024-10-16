package sql.dml;

import org.junit.jupiter.api.Test;
import persistence.sql.dml.Person;
import persistence.sql.dml.QueryBuilder;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryBuilderTest {

    QueryBuilder queryBuilder = new QueryBuilder(Person.class);

    @Test
    void getColumns() {
        String query = queryBuilder.getColumns();
        assertThat(query).isEqualTo("nick_name, old, email");
    }

    @Test
    void getValue() {
        String query = queryBuilder.getValue();
//        assertThat(query).isEqualTo()
    }
//INSERT INTO users (nick_name, old, email) VALUES ('jskim', 33, 'qazwsx3745@naver.com');
}
