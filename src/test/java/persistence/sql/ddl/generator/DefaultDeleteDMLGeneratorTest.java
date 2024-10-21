package persistence.sql.ddl.generator;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Table;
import persistence.sql.ddl.Person;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultDeleteDMLGeneratorTest {
    @Test
    void deleteAll_DML을_생성한다() {
        Table table = Table.from(Person.class);
        DefaultDeleteDMLGenerator generator = new DefaultDeleteDMLGenerator();

        String dml = generator.generateDeleteAll(table);

        assertThat(dml).isEqualTo("delete from users;");
    }

    @Test
    void deleteById_DML을_생성한다() {
        Table table = Table.from(Person.class);
        DefaultDeleteDMLGenerator generator = new DefaultDeleteDMLGenerator();

        String dml = generator.generateDeleteById(table, 3);

        assertThat(dml).isEqualTo("delete from users where id = 3;");
    }
}
