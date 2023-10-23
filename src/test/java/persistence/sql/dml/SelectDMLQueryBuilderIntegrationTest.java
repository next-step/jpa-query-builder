package persistence.sql.dml;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import persistence.entity.Person;
import persistence.entity.PersonFixtures;
import persistence.sql.dbms.DbmsStrategy;
import persistence.sql.ddl.CreateDDLQueryBuilder;
import persistence.sql.dml.clause.WhereClause;
import persistence.sql.dml.clause.operator.Operator;
import persistence.testutils.ReflectionTestSupport;
import persistence.testutils.TestQueryExecuteSupport;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class SelectDMLQueryBuilderIntegrationTest extends TestQueryExecuteSupport {
    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("DELETE FROM USERS;");
    }

    @DisplayName("전체 (findAll) 조회")
    @Test
    void executeSelectDmlQuery_findAll() {
        // given
        List<Person> persons = Arrays.asList(
                PersonFixtures.fixture(1L, "name1", 20, "email1"),
                PersonFixtures.fixture(2L, "name2", 20, "email2"),
                PersonFixtures.fixture(3L, "name3", 20, "email3")
        );
        savePersonFixtures(persons);
        SelectQuery findAllSelectQuery = SelectQuery.select();

        // when
        SelectDMLQueryBuilder<Person> selectDMLQueryBuilder = new SelectDMLQueryBuilder<>(DbmsStrategy.H2, Person.class, findAllSelectQuery);

        // then
        List<Person> selectQueryResultPersons = jdbcTemplate.query(selectDMLQueryBuilder.build(), resultSet -> {
            int id = resultSet.getInt("ID");
            String name = resultSet.getString("NICK_NAME");
            int age = resultSet.getInt("OLD");
            String email = resultSet.getString("EMAIL");

            return PersonFixtures.fixture(id, name, age, email);
        });

        assertAll(
                () -> assertThat(selectQueryResultPersons).hasSize(3),
                () -> assertThat(selectQueryResultPersons.stream()
                        .map(it -> ReflectionTestSupport.getFieldValue(it, "id"))
                ).containsExactly(1L, 2L, 3L),
                () -> assertThat(selectQueryResultPersons.stream()
                        .map(it -> ReflectionTestSupport.getFieldValue(it, "name"))
                ).containsExactly("name1", "name2", "name3"),
                () -> assertThat(selectQueryResultPersons.stream()
                        .map(it -> ReflectionTestSupport.getFieldValue(it, "email"))
                ).containsExactly("email1", "email2", "email3")
        );
    }

    @DisplayName("조건 (findBy) 조회")
    @ParameterizedTest
    @ArgumentsSource(FindByTestWhereClauseArgumentProvider.class)
    void executeSelectDmlQuery_findAll(WhereClause whereClause, int expectedSize, List<Long> expectedIds, List<String> expectedNames, List<String> expectedEmails) {
        // given
        List<Person> persons = Arrays.asList(
                PersonFixtures.fixture(4L, "name4", 20, "email4"),
                PersonFixtures.fixture(5L, "name5", 20, "email5"),
                PersonFixtures.fixture(6L, "name6", 20, "email6")
        );
        savePersonFixtures(persons);
        SelectQuery findAllSelectQuery = SelectQuery.select()
                .where(whereClause);

        // when
        SelectDMLQueryBuilder<Person> selectDMLQueryBuilder = new SelectDMLQueryBuilder<>(DbmsStrategy.H2, Person.class, findAllSelectQuery);

        // then
        List<Person> selectQueryResultPersons = jdbcTemplate.query(selectDMLQueryBuilder.build(), resultSet -> {
            int id = resultSet.getInt("ID");
            String name = resultSet.getString("NICK_NAME");
            int age = resultSet.getInt("OLD");
            String email = resultSet.getString("EMAIL");

            return PersonFixtures.fixture(id, name, age, email);
        });

        assertAll(
                () -> assertThat(selectQueryResultPersons).hasSize(expectedSize),
                () -> assertThat(selectQueryResultPersons.stream()
                        .map(it -> ReflectionTestSupport.getFieldValue(it, "id"))
                        .collect(Collectors.toList())
                ).isEqualTo(expectedIds),
                () -> assertThat(selectQueryResultPersons.stream()
                        .map(it -> ReflectionTestSupport.getFieldValue(it, "name"))
                        .collect(Collectors.toList())
                ).isEqualTo(expectedNames),
                () -> assertThat(selectQueryResultPersons.stream()
                        .map(it -> ReflectionTestSupport.getFieldValue(it, "email"))
                        .collect(Collectors.toList())
                ).isEqualTo(expectedEmails)
        );
    }

    static class FindByTestWhereClauseArgumentProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of(
                            WhereClause.of("ID", 4L, Operator.EQUALS),
                            1,
                            Arrays.asList(4L),
                            Arrays.asList("name4"),
                            Arrays.asList("email4")
                    ),
                    Arguments.of(
                            WhereClause.of("NICK_NAME", "name5", Operator.EQUALS),
                            1,
                            Arrays.asList(5L),
                            Arrays.asList("name5"),
                            Arrays.asList("email5")
                    ),
                    Arguments.of(
                            WhereClause.of("OLD", 20, Operator.EQUALS),
                            3,
                            Arrays.asList(4L, 5L, 6L),
                            Arrays.asList("name4", "name5", "name6"),
                            Arrays.asList("email4", "email5", "email6")
                    ),
                    Arguments.of(
                            WhereClause.of("ID", 5L, Operator.GREATER_THAN_OR_EQUALS),
                            2,
                            Arrays.asList(5L, 6L),
                            Arrays.asList("name5", "name6"),
                            Arrays.asList("email5", "email6")
                    )
            );
        }
    }

    private void savePersonFixtures(List<Person> persons) {
        CreateDDLQueryBuilder<Person> createDDLQueryBuilder = new CreateDDLQueryBuilder<>(DbmsStrategy.H2, Person.class);
        String createQuery = createDDLQueryBuilder.build();
        jdbcTemplate.execute(createQuery.replace("CREATE TABLE USERS", "CREATE TABLE IF NOT EXISTS PUBLIC.USERS"));

        for (Person person : persons) {
            InsertDMLQueryBuilder<Person> insertDMLQueryBuilder = new InsertDMLQueryBuilder<>(DbmsStrategy.H2, person);
            jdbcTemplate.execute(insertDMLQueryBuilder.build());
        }
    }
}
