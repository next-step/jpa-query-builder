package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;

import static org.assertj.core.api.Assertions.assertThat;

class DmlGeneratorTest {

    @DisplayName("엔티티를 저장하는 INSERT 쿼리를 생성한다.")
    @Test
    void insert() {
        Person person = new Person("jack", 30, "jack@abc.com");
        DmlGenerator generator = new DmlGenerator(Person.class);
        String query = generator.generateInsertQuery(person);
        assertThat(query).isEqualTo("INSERT INTO users ( nick_name, old, email ) VALUES ( 'jack', 30, 'jack@abc.com' )");
    }

}
