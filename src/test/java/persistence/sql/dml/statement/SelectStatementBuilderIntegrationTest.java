package persistence.sql.dml.statement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import database.DatabaseServer;
import database.H2;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.generator.CreateDDLQueryGenerator;
import persistence.sql.ddl.generator.DropDDLQueryGenerator;
import persistence.sql.dialect.H2ColumnType;
import persistence.sql.dml.Database;
import persistence.sql.dml.JdbcTemplate;
import persistence.sql.dml.clause.WherePredicate;
import persistence.sql.dml.clause.operator.EqualOperator;

@DisplayName("SELECT 문 생성 통합 테스트")
class SelectStatementBuilderIntegrationTest {

    private DatabaseServer server;

    private Database jdbcTemplate;

    @BeforeEach
    void setUp() throws SQLException {
        server = new H2();
        server.start();

        jdbcTemplate = new JdbcTemplate(server.getConnection());
        CreateDDLQueryGenerator createDDLQueryGenerator = new CreateDDLQueryGenerator(new H2ColumnType());
        jdbcTemplate.execute(createDDLQueryGenerator.create(SelectStatementBuilderFixture.class));
    }

    @AfterEach
    void tearDown() {
        DropDDLQueryGenerator dropDDLQueryGenerator = new DropDDLQueryGenerator(new H2ColumnType());
        jdbcTemplate.execute(dropDDLQueryGenerator.drop(SelectStatementBuilderFixture.class));
        server.stop();
    }

    @Test
    @DisplayName("SelectStatementBuilder를 통해 findAll()과 같은 Select문으로 갖고 올 수 있다.")
    void buildFindAllSelectBuilder() throws SQLException {

        //given
        final String expectedName = "key";
        final String expectedEmail = "key@gmail.com";
        final InsertStatementBuilder insertStatementBuilder = new InsertStatementBuilder(new H2ColumnType());

        SelectStatementBuilderFixture fixture1 = new SelectStatementBuilderFixture(expectedName + 1, expectedEmail);
        SelectStatementBuilderFixture fixture2 = new SelectStatementBuilderFixture(expectedName + 2, expectedEmail);
        final String insert1 = insertStatementBuilder.insert(fixture1);
        final String insert2 = insertStatementBuilder.insert(fixture2);
        jdbcTemplate.execute(insert1);
        jdbcTemplate.execute(insert2);

        //when
        int totalRowCount;
        final String selectStatement = SelectStatementBuilder.builder()
            .select(SelectStatementBuilderFixture.class, new H2ColumnType())
            .build();

        try (final ResultSet resultSet = jdbcTemplate.executeQuery(selectStatement)) {
            resultSet.last();
            totalRowCount = resultSet.getRow();
        }

        //then
        assertThat(totalRowCount).isEqualTo(2);
    }

    @Test
    @DisplayName("SelectStatementBuilder를 통해 findById()과 같은 Select문으로 갖고 올 수 있다.")
    void buildFindByIdSelectBuilder() throws SQLException {

        //given
        final String expectedName1 = "key1";
        final String expectedName2 = "key2";
        final String expectedEmail1 = expectedName1 + "@gmail.com";
        final String expectedEmail2 = expectedName2 + "@gmail.com";
        final InsertStatementBuilder insertStatementBuilder = new InsertStatementBuilder(new H2ColumnType());

        SelectStatementBuilderFixture fixture1 = new SelectStatementBuilderFixture(expectedName1, expectedEmail1);
        SelectStatementBuilderFixture fixture2 = new SelectStatementBuilderFixture(expectedName2, expectedEmail2);
        final String insert1 = insertStatementBuilder.insert(fixture1);
        final String insert2 = insertStatementBuilder.insert(fixture2);
        jdbcTemplate.execute(insert1);
        jdbcTemplate.execute(insert2);

        //when
        int totalRowCount;
        final String conditionColumnName = "id";
        final int conditionValue = 1;

        final String selectStatementById = SelectStatementBuilder.builder()
            .select(SelectStatementBuilderFixture.class, new H2ColumnType())
            .where(WherePredicate.of(conditionColumnName, conditionValue, new EqualOperator()))
            .build();

        Object idValue;
        Object nameValue;
        Object emailValue;

        try (final ResultSet resultSet = jdbcTemplate.executeQuery(selectStatementById)) {
            resultSet.next();

            idValue = resultSet.getObject("ID");
            nameValue = resultSet.getObject("NAME");
            emailValue = resultSet.getObject("EMAIL");

            resultSet.last();
            totalRowCount = resultSet.getRow();
        }

        //then
        assertAll(
            () -> assertThat(totalRowCount).isEqualTo(1),
            () -> assertThat(idValue).isEqualTo(1L),
            () -> assertThat(nameValue).isEqualTo(expectedName1),
            () -> assertThat(emailValue).isEqualTo(expectedEmail1)
        );
    }

    @Entity
    static class SelectStatementBuilderFixture {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        private String email;

        public SelectStatementBuilderFixture(String name, String email) {
            this.name = name;
            this.email = email;
        }

        protected SelectStatementBuilderFixture() {
        }
    }
}