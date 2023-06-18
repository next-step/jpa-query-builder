package persistence.ddl.database;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.Test;
import persistence.*;
import persistence.ddl.CreateTableBuilder;
import persistence.ddl.DeleteBuilder;
import persistence.ddl.InsertBuilder;
import persistence.ddl.SelectBuilder;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

class H2JdbcTemplateTest {

    @Test
    void test() throws SQLException {
        final DatabaseServer server = new H2();
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

        EntityReflectionManager entityReflectionManager = new EntityReflectionManager(Person.class);
        Table table = entityReflectionManager.table();
        Columns columns = entityReflectionManager.columns();

        CreateTableBuilder createTableBuilder = new CreateTableBuilder(table, columns);

        Person person = new Person(1L, "slow", 3, "slow@email.com");

        ColumnMap columnsMap = entityReflectionManager.columnValueMap(person);
        InsertBuilder insertBuilder = new InsertBuilder(entityReflectionManager.table(), columnsMap);

        jdbcTemplate.execute(createTableBuilder.query());
        jdbcTemplate.execute(insertBuilder.query());


        PersonDatabase personDatabase = new PersonDatabase(jdbcTemplate);
        ReflectiveRowMapper<Person> reflectiveRowMapper = new ReflectiveRowMapper<>(Person.class);

        SelectBuilder selectBuilder = new SelectBuilder(table, entityReflectionManager.columns());
        // 전체 조회
        List<Person> 전체조회Person = personDatabase.query(selectBuilder.findAllQuery().toUpperCase());

        assertThat(전체조회Person).containsExactly(person);

        // 단건 조회
        Person 단건조회Person = personDatabase.executeQuery(selectBuilder.findById(1L));

        assertThat(단건조회Person).isEqualTo(person);

        // 삭제
        DeleteBuilder deleteBuilder = new DeleteBuilder(table);
        personDatabase.execute(deleteBuilder.query("id", 1L));

        assertNull(personDatabase.executeQuery(selectBuilder.findById(1L)));
    }

}
