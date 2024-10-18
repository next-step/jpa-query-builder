package sql.dml;

import org.junit.jupiter.api.Test;
import persistence.sql.dml.Person;
import persistence.sql.dml.QueryBuilder;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryBuilderTest {

    QueryBuilder queryBuilder = new QueryBuilder(Person.class);


    @Test
    void getColumnName() {

        String columnName = queryBuilder.getColumnName();
    }

    @Test
    void getColumnPart() {
        String query = queryBuilder.getColumnPart();
        assertThat(query).isEqualTo("nick_name, old, email");
    }

    @Test
    void getValuePart() {
        String query = queryBuilder.getValuePart();
//        assertThat(query).isEqualTo()
    }
//INSERT INTO users (nick_name, old, email) VALUES ('jskim', 33, 'qazwsx3745@naver.com');
}
