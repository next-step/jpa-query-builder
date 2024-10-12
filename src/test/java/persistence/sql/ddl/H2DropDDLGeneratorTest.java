package persistence.sql.ddl;

import org.junit.jupiter.api.Test;

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
