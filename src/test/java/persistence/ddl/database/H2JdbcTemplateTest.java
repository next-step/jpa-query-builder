package persistence.ddl.database;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.Test;
import persistence.ColumnMap;
import persistence.Columns;
import persistence.EntityReflectionManager;
import persistence.Table;
import persistence.ddl.CreateTableBuilder;
import persistence.ddl.InsertBuilder;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class H2JdbcTemplateTest {

    @Test
    void test() throws SQLException {
        final DatabaseServer server = new H2();
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
        final PersonRepository personRepository = new PersonRepository(jdbcTemplate);

        EntityReflectionManager entityReflectionManager = new EntityReflectionManager(Person.class);
        Table table = entityReflectionManager.table();
        Columns columns = entityReflectionManager.columns();

        CreateTableBuilder createTableBuilder = new CreateTableBuilder(table, columns);

        Person person = new Person(1L, "slow", 3, "slow@email.com");

        ColumnMap columnsMap = entityReflectionManager.columnValueMap(person);
        InsertBuilder insertBuilder = new InsertBuilder(entityReflectionManager.table(), columnsMap);

        jdbcTemplate.execute(createTableBuilder.query());
        jdbcTemplate.execute(insertBuilder.query());

        List<Person> findAllObject = personRepository.findAll();

        assertThat(findAllObject).containsExactly(person);
    }

}
