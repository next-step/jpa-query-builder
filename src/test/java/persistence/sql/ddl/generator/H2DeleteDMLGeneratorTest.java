package persistence.sql.ddl.generator;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.EntityFields;
import persistence.sql.ddl.Person;

import static org.assertj.core.api.Assertions.assertThat;

class H2DeleteDMLGeneratorTest {
    @Test
    void deleteAll_DML을_생성한다() {
        EntityFields entityFields = EntityFields.from(Person.class);
        H2DeleteDMLGenerator generator = new H2DeleteDMLGenerator();

        String dml = generator.generateDeleteAll(entityFields);

        assertThat(dml).isEqualTo("delete from users;");
    }

    @Test
    void deleteById_DML을_생성한다() {
        EntityFields entityFields = EntityFields.from(Person.class);
        H2DeleteDMLGenerator generator = new H2DeleteDMLGenerator();

        String dml = generator.generateDeleteById(entityFields, 3);

        assertThat(dml).isEqualTo("delete from users where id = 3;");
    }
}
