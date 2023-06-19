package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;

import static org.assertj.core.api.Assertions.assertThat;

class DmlGeneratorTest {

    private final DmlGenerator generator = new DmlGenerator(Person.class);

    @DisplayName("엔티티를 저장하는 INSERT 쿼리를 생성한다.")
    @Test
    void insert() {
        Person person = new Person("jack", 30, "jack@abc.com");
        String query = generator.generateInsertQuery(person);
        assertThat(query).isEqualTo("INSERT INTO users ( nick_name, old, email ) VALUES ( 'jack', 30, 'jack@abc.com' )");
    }

    @DisplayName("저장된 모든 엔티티를 조회하는 SELECT 쿼리를 생성한다.")
    @Test
    void findAll() {
        String query = generator.generateFindAllQuery();
        assertThat(query).isEqualTo("SELECT id, nick_name, old, email FROM users");
    }

    @DisplayName("식별자로 하나의 엔티티를 조회하는 SELECT 쿼리를 생성한다.")
    @Test
    void findById() {
        String query = generator.generateFindByIdQuery(1L);
        assertThat(query).isEqualTo("SELECT id, nick_name, old, email FROM users WHERE id = 1");
    }
}
