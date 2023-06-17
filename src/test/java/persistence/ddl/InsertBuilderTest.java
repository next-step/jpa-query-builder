package persistence.ddl;

import domain.Person;
import org.junit.jupiter.api.Test;
import persistence.ColumnMap;
import persistence.EntityReflectionManager;

import static org.assertj.core.api.Assertions.assertThat;

class InsertBuilderTest {

    @Test
    void test() {
        EntityReflectionManager entityReflectionManager = new EntityReflectionManager(Person.class);
        Person person = new Person("slow", 1, "slow@email.com");

        ColumnMap columns = entityReflectionManager.columnValueMap(person);
        InsertBuilder actual = new InsertBuilder(entityReflectionManager.table(), columns);

        assertThat(actual.query()).isEqualTo("insert into Person (nick_name,old,email) values ('slow','1','slow@email.com')");
    }
}
