package persistence.sql.dml;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import persistence.entity.Person;
import persistence.entity.PersonFixtures;
import persistence.sql.dbms.Dialect;
import persistence.sql.ddl.CreateDDLQueryBuilder;
import persistence.sql.dml.clause.WhereClause;
import persistence.sql.dml.clause.operator.Operator;
import persistence.testutils.ReflectionTestSupport;
import persistence.testutils.TestQueryExecuteSupport;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class SelectDMLQueryBuilderIntegrationTest extends TestQueryExecuteSupport {
    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS USERS;");
        List<Person> persons = Arrays.asList(
                PersonFixtures.fixture(1L, "name1", 20, "email1"),
                PersonFixtures.fixture(2L, "name2", 20, "email2"),
                PersonFixtures.fixture(3L, "name3", 20, "email3")
        );
        savePersonFixtures(persons);
    }

    @DisplayName("전체 (findAll) 조회")
    @Test
    void executeSelectDmlQuery_findAll() {
        // when
        SelectDMLQueryBuilder<Person> selectDMLQueryBuilder = new SelectDMLQueryBuilder<>(Dialect.H2, Person.class);

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
        // when
        SelectDMLQueryBuilder<Person> selectDMLQueryBuilder = new SelectDMLQueryBuilder<>(Dialect.H2, Person.class)
                .where(whereClause);

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
                            WhereClause.of("ID", 1L, Operator.EQUALS),
                            1,
                            Arrays.asList(1L),
                            Arrays.asList("name1"),
                            Arrays.asList("email1")
                    ),
                    Arguments.of(
                            WhereClause.of("NICK_NAME", "name2", Operator.EQUALS),
                            1,
                            Arrays.asList(2L),
                            Arrays.asList("name2"),
                            Arrays.asList("email2")
                    ),
                    Arguments.of(
                            WhereClause.of("OLD", 20, Operator.EQUALS),
                            3,
                            Arrays.asList(1L, 2L, 3L),
                            Arrays.asList("name1", "name2", "name3"),
                            Arrays.asList("email1", "email2", "email3")
                    ),
                    Arguments.of(
                            WhereClause.of("ID", 2L, Operator.GREATER_THAN_OR_EQUALS),
                            2,
                            Arrays.asList(2L, 3L),
                            Arrays.asList("name2", "name3"),
                            Arrays.asList("email2", "email3")
                    )
            );
        }
    }

    private void savePersonFixtures(List<Person> persons) {
        CreateDDLQueryBuilder<Person> createDDLQueryBuilder = new CreateDDLQueryBuilder<>(Dialect.H2, Person.class);
        String createQuery = createDDLQueryBuilder.build();
        jdbcTemplate.execute(createQuery.replace("CREATE TABLE USERS", "CREATE TABLE IF NOT EXISTS PUBLIC.USERS"));

        for (Person person : persons) {
            InsertDMLQueryBuilder<Person> insertDMLQueryBuilder = new InsertDMLQueryBuilder<>(Dialect.H2, person);
            jdbcTemplate.execute(insertDMLQueryBuilder.build());
        }
    }
}
