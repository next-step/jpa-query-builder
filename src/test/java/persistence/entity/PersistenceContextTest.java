package persistence.entity;

import entity.Person;
import org.junit.jupiter.api.Test;
import persistence.entitiy.context.PersistencContext;

public class PersistenceContextTest {

    @Test
    void test() {
        PersistencContext persistencContext = new PersistencContext();
        Person person = new Person(1L, "민준", 29, "민준.com");
        persistencContext.manage(person);
    }
}
