package persistence.sql.dml.statement;

import static org.assertj.core.api.Assertions.assertThat;

import database.DatabaseServer;
import database.H2;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.generator.CreateDDLQueryGenerator;
import persistence.sql.ddl.generator.DropDDLQueryGenerator;
import persistence.sql.ddl.generator.fixture.PersonV3;
import persistence.sql.dialect.H2ColumnType;
import persistence.sql.dml.Database;
import persistence.sql.dml.JdbcTemplate;

@DisplayName("INSERT 문 생성 통합 테스트")
class InsertStatementBuilderIntegrationTest {

    private DatabaseServer server;

    private Database jdbcTemplate;

    @BeforeEach
    void setUp() throws SQLException {
        server = new H2();
        server.start();

        jdbcTemplate = new JdbcTemplate(server.getConnection());
        CreateDDLQueryGenerator createDDLQueryGenerator = new CreateDDLQueryGenerator(new H2ColumnType());
        jdbcTemplate.execute(createDDLQueryGenerator.create(PersonV3.class));
    }

    @AfterEach
    void tearDown() {
        DropDDLQueryGenerator dropDDLQueryGenerator = new DropDDLQueryGenerator(new H2ColumnType());
        jdbcTemplate.execute(dropDDLQueryGenerator.drop(PersonV3.class));
        server.stop();
    }

    @Test
    @DisplayName("InsertStatementBuilder를 통해 Entity Object를 INSERT 문으로 매핑하여 저장할 수 있다.")
    void canInsertRowByEntityObject() throws SQLException {
        //given
        InsertStatementBuilder insertStatementBuilder = new InsertStatementBuilder(new H2ColumnType());
        PersonV3 person1 = new PersonV3("유저1", 20, "user1@jpa.com", 1);
        PersonV3 person2 = new PersonV3("유저2", 21, "user2@jpa.com", 2);
        PersonV3 person3 = new PersonV3("유저3", 25, "user3@jpa.com", 3);
        PersonV3 person4 = new PersonV3("유저4", 29, "user4@jpa.com", 4);

        final String person1Insert = insertStatementBuilder.insert(person1);
        final String person2Insert = insertStatementBuilder.insert(person2);
        final String person3Insert = insertStatementBuilder.insert(person3);
        final String person4Insert = insertStatementBuilder.insert(person4);

        jdbcTemplate.execute(person1Insert);
        jdbcTemplate.execute(person2Insert);
        jdbcTemplate.execute(person3Insert);
        jdbcTemplate.execute(person4Insert);

        //expect
        final String countAllSelectStatement = "SELECT count(*) as count FROM USERS";
        try (final ResultSet resultSet = jdbcTemplate.executeQuery(countAllSelectStatement)) {
            resultSet.next();
            assertThat(resultSet.getObject("count")).isEqualTo(4L);
        }
    }
}