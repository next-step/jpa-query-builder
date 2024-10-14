package persistence.sql.ddl.generator;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.EntityFields;
import persistence.sql.ddl.Person;
import persistence.sql.ddl.generator.H2DropDDLGenerator;

import static org.assertj.core.api.Assertions.assertThat;

class H2DropDDLGeneratorTest {
    @Test
    void DDL을_생성한다() {
        EntityFields entityFields = EntityFields.from(Person.class);
        H2DropDDLGenerator h2DropDDLGenerator = new H2DropDDLGenerator();

        String ddl = h2DropDDLGenerator.generate(entityFields);

        assertThat(ddl).isEqualTo("DROP TABLE users;");
    }
}