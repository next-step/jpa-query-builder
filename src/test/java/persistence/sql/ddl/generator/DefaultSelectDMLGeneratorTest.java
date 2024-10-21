package persistence.sql.ddl.generator;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Table;
import persistence.sql.ddl.Person;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultSelectDMLGeneratorTest {
    @Test
    void findAll_DML을_생성한다() {
        Table table = Table.from(Person.class);
        DefaultSelectDMLGenerator generator = new DefaultSelectDMLGenerator();

        String dml = generator.generateFindAll(table);

        assertThat(dml).isEqualTo("select * from users;");
    }

    @Test
    void findById_DML을_생성한다() {
        Table table = Table.from(Person.class);
        DefaultSelectDMLGenerator generator = new DefaultSelectDMLGenerator();

        String dml = generator.generateFindById(table, 3);

        assertThat(dml).isEqualTo("select * from users where id = 3;");
    }
}
