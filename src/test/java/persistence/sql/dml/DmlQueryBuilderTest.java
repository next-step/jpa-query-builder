package persistence.sql.dml;

import database.DatabaseServer;
import database.H2;
import domain.Person1;
import domain.Person3;
import domain.step3.mapper.RowMapperImpl;
import jakarta.persistence.Column;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.DdlQueryBuilder;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Arrays;

import static domain.step3.utils.StringUtils.isBlankOrEmpty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class DmlQueryBuilderTest {

    static DatabaseServer server;
    static JdbcTemplate jdbcTemplate;
    DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder(new domain.step2.dialect.H2Dialect());
    DmlQueryBuilder dmlQueryBuilder = new DmlQueryBuilder(new domain.step3.dialect.H2Dialect());

    Person3 person = new Person3();
    Person3 personWithIdNull = new Person3();
    RowMapper<Person3> rowMapper = new RowMapperImpl<>(Person3.class);

    @BeforeAll
    static void setUp() throws SQLException {
        server = new H2();
        server.start();
        jdbcTemplate = new JdbcTemplate(server.getConnection());
    }

    @AfterAll
    static void destroy() {
        server.stop();
    }

    @DisplayName("모든 데이터가 주어질 때 데이터 insert test")
    @Test
    void insertDataWithAllValuesTest() {
        person = new Person3(1L, "test", 20, "test@test.com");
        String insertQuery = "INSERT INTO users (id, nick_name, old, email) VALUES (1, 'test', 20, 'test@test.com');";
        assertThat(dmlQueryBuilder.insertQuery(person)).isEqualTo(insertQuery);
    }

    @DisplayName("nullable = false 인 경우를 제외하고 다른 데이터는 null 일 경우 insert test")
    @Test
    void insertDataWithNullValuesTest() {
        person = new Person3(null, null, "test@test.com");
        String insertQuery = "INSERT INTO users (id, nick_name, old, email) VALUES (null, null, null, 'test@test.com');";
        assertThat(dmlQueryBuilder.insertQuery(person)).isEqualTo(insertQuery);
    }

    @DisplayName("nullable = false 인 컬럼에 null 이 들어간 경우")
    @Test
    void insertDataWithNullableExceptionTest() {
        personWithIdNull = new Person3(null, null, null);
        assertAll(
                () -> assertThatThrownBy(() -> dmlQueryBuilder.insertQuery(personWithIdNull))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> dmlQueryBuilder.insertQuery(personWithIdNull))
                        .hasMessage("fieldValue 가 null 이어서는 안됩니다.")
        );
    }

    @DisplayName("Person1 의 경우 Id 에 GeneratedValue 가 없는데 null 이 들어간 경우")
    @Test
    void insertDataWithIdExceptionTest() {
        Person1 person1 = new Person1(null, null, null);
        assertAll(
                () -> assertThatThrownBy(() -> dmlQueryBuilder.insertQuery(person1))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> dmlQueryBuilder.insertQuery(person1))
                        .hasMessage("fieldValue 가 null 이어서는 안됩니다.")
        );
    }

    @DisplayName("findAll 테스트")
    @Test
    void executeFindAllQuery() {
        createTable();
        insertData();
        assertThat(jdbcTemplate.query(dmlQueryBuilder.findAllQuery(Person3.class), rowMapper)).hasSize(2);
        dropTable();
    }

    @DisplayName("findById 테스트")
    @Test
    void executeFindByIdQuery() {
        createTable();
        insertData();
        assertThat(jdbcTemplate.query(dmlQueryBuilder.findByIdQuery(Person3.class, personWithIdNull.getId()), rowMapper))
                .hasSize(1);
        dropTable();
    }

    @DisplayName("delete 테스트")
    @Test
    void executeDeleteQuery() {
        createTable();
        insertData();

        assertThat(jdbcTemplate.query(dmlQueryBuilder.findAllQuery(Person3.class), rowMapper)).hasSize(2);

        Field idField = Arrays.stream(person.getClass().getDeclaredFields())
                .filter(field1 -> getFieldName(field1).equals("id"))
                .findAny().get();

        jdbcTemplate.execute(dmlQueryBuilder.deleteQuery(Person3.class, idField, person.getId()));
        assertThat(jdbcTemplate.query(dmlQueryBuilder.findAllQuery(Person3.class), rowMapper)).hasSize(1);
        dropTable();
    }

    private void createTable() {
        jdbcTemplate.execute(ddlQueryBuilder.createTable(Person3.class));
    }

    private void insertData() {
        person = new Person3(1L, "test", 20, "test@test.com");
        Long person1Seq = jdbcTemplate.executeAndReturnKey(dmlQueryBuilder.insertQuery(person));

        personWithIdNull = new Person3(null, null, "test@test.com");
        if (personWithIdNull.isIdValueNull()) {
            personWithIdNull.updateIdValue(person1Seq + 1);
            jdbcTemplate.execute(dmlQueryBuilder.insertQuery(personWithIdNull));
        }
    }

    private void dropTable() {
        jdbcTemplate.execute(ddlQueryBuilder.dropTable(Person3.class));
    }

    private String getFieldName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            return isBlankOrEmpty(field.getAnnotation(Column.class).name()) ? field.getName()
                    : field.getAnnotation(Column.class).name();
        }
        return field.getName();
    }
}
