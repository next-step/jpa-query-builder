package persistence.ddl.database;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.ColumnMap;
import persistence.Columns;
import persistence.EntityReflectionManager;
import persistence.Table;
import persistence.ddl.CreateTableBuilder;
import persistence.ddl.DeleteBuilder;
import persistence.ddl.InsertBuilder;
import persistence.ddl.SelectBuilder;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

class H2JdbcTemplateTest {
    private final Person PERSON = new Person(1L, "slow", 3, "slow@email.com");

    private JdbcTemplate jdbcTemplate;
    private EntityReflectionManager entityReflectionManager;
    private Table table;
    private Columns columns;
    private PersonDatabase personDatabase;
    private ColumnMap columnsMap;

    @BeforeEach
    void beforeEach() throws SQLException {
        jdbcTemplate = new JdbcTemplate(new H2().getConnection());
        entityReflectionManager = new EntityReflectionManager(Person.class);
        table = entityReflectionManager.table();
        columns = entityReflectionManager.columns();
        columnsMap = entityReflectionManager.columnValueMap(PERSON);
        personDatabase = new PersonDatabase(jdbcTemplate);
    }

    @Test
    void test() {
        CreateTableBuilder createTableBuilder = new CreateTableBuilder(table, columns);

        // create Table
        jdbcTemplate.execute(createTableBuilder.query());

        // insert row
        InsertBuilder insertBuilder = new InsertBuilder(entityReflectionManager.table(), columnsMap);
        jdbcTemplate.execute(insertBuilder.query());

        // 전체 조회
        SelectBuilder selectBuilder = new SelectBuilder(table, entityReflectionManager.columns());
        List<Person> 전체조회Person = personDatabase.query(selectBuilder.findAllQuery().toUpperCase());
        assertThat(전체조회Person).containsExactly(PERSON);

        // 단건 조회
        Person 단건조회Person = personDatabase.executeQuery(selectBuilder.findById(1L));
        assertThat(단건조회Person).isEqualTo(PERSON);

        // 삭제
        DeleteBuilder deleteBuilder = new DeleteBuilder(table);
        personDatabase.execute(deleteBuilder.query("id", 1L));

        assertNull(personDatabase.executeQuery(selectBuilder.findById(1L)));
    }

}
