package persistence.sql.ddl.generator;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.EntityFields;
import persistence.sql.ddl.Person;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultDropDDLGeneratorTest {
    @Test
    void DDL을_생성한다() {
        EntityFields entityFields = EntityFields.from(Person.class);
        DefaultDropDDLGenerator defaultDropDDLGenerator = new DefaultDropDDLGenerator();

        String ddl = defaultDropDDLGenerator.generate(entityFields);

        assertThat(ddl).isEqualTo("DROP TABLE users;");
    }
}
