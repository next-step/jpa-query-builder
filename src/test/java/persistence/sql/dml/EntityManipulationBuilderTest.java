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

        String query = entityManipulationBuilder.insert();

        assertThat(query).isEqualTo("INSERT INTO users (nick_name, old, email) " +
                "VALUES ('John Doe', 30, 'john.doe@example.com');");
    }

}