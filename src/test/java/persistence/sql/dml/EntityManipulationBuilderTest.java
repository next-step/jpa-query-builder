package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;

import static org.assertj.core.api.Assertions.assertThat;

class EntityManipulationBuilderTest {

    @Test
    @DisplayName("Person 엔터티 insert 쿼리 만들기")
    public void insertQueryTest() {
        EntityManipulationBuilder entityManipulationBuilder = new EntityManipulationBuilder(Person.class);

        Person person = new Person("John Doe",  30, "john.doe@example.com");
        String query = entityManipulationBuilder.insert(person);

        assertThat(query).isEqualTo("INSERT INTO users (nick_name, old, email) " +
                "VALUES ('John Doe', 30, 'john.doe@example.com');");
    }

    @Test
    @DisplayName("Person 엔터티 findAll 쿼리 만들기")
    public void findAllQueryTest() {
        EntityManipulationBuilder entityManipulationBuilder = new EntityManipulationBuilder(Person.class);

        String query = entityManipulationBuilder.findAll(Person.class);

        assertThat(query).isEqualTo("SELECT id, nick_name, old, email FROM users;");
    }

}
