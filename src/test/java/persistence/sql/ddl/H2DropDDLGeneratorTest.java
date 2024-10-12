package persistence.sql.ddl;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class H2DropDDLGeneratorTest {
    @Test
    public void DDL을_생성한다() {
        Entity entity = Entity.of(Person.class);
        H2DropDDLGenerator h2DropDDLGenerator = new H2DropDDLGenerator();

        String ddl = h2DropDDLGenerator.generate(entity);

        assertThat(ddl).isEqualTo("DROP TABLE users;");
    }
}
