package persistence.sql.ddl.generator;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.EntityFields;
import persistence.sql.ddl.Person;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultSelectDMLGeneratorTest {
    @Test
    void findAll_DML을_생성한다() {
        EntityFields entityFields = EntityFields.from(Person.class);
        DefaultSelectDMLGenerator generator = new DefaultSelectDMLGenerator();

        String dml = generator.generateFindAll(entityFields);

        assertThat(dml).isEqualTo("select * from users;");
    }

    @Test
    void findById_DML을_생성한다() {
        EntityFields entityFields = EntityFields.from(Person.class);
        DefaultSelectDMLGenerator generator = new DefaultSelectDMLGenerator();

        String dml = generator.generateFindById(entityFields, 3);

        assertThat(dml).isEqualTo("select * from users where id = 3;");
    }
}
