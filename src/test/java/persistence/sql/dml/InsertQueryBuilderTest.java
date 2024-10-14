package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.domain.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InsertQueryBuilderTest {

    @Test
    @DisplayName("Person 객체로 Insert Query 만들기")
    void createTableQuery() {
        Person person = new Person("양승인", 33, "rhfpdk92@naver.com", 1);
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(Person.class);
        String insertQuery = insertQueryBuilder.getInsertQuery(person);

        assertEquals(insertQuery, "insert into users (nick_name, old, email) VALUES ('양승인', 33, 'rhfpdk92@naver.com')");
    }


}
