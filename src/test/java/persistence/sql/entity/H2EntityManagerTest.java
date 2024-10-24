package persistence.sql.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class H2EntityManagerTest {
    @DisplayName("Person 객체를 저장한다.")
    @Test
    void persist() {
        final EntityManager entityManager = new H2EntityManager();
        final Person person = new Person(1L, "Kent Beck", 64, "beck@example.com");
        assertDoesNotThrow(() -> entityManager.persist(person));
    }
}
