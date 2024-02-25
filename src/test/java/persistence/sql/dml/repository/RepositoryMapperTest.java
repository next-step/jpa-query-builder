package persistence.sql.dml.repository;

import jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.db.H2Database;
import persistence.sql.dml.query.builder.SelectQueryBuilder;
import persistence.sql.entity.EntityMappingTable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class RepositoryMapperTest extends H2Database {
    private RepositoryMapper<Person> repositoryMapper;
    private Connection connection;
    private SelectQueryBuilder selectQueryBuilder;
    private Repository<Person> personRepository;

    private Person person1;
    private Person person2;


    @BeforeEach
    void setUp() throws SQLException {
        this.personRepository = new RepositoryImpl<>(jdbcTemplate, Person.class);
        this.repositoryMapper = new RepositoryMapper(Person.class);
        this.connection = server.getConnection();
        this.selectQueryBuilder = SelectQueryBuilder.from(entityMappingTable);

        personRepository.deleteAll();

        person1 = new Person(1L, "박재성", 10, "jason");

        personRepository.save(person1);
    }


    @DisplayName("디비에서 조회된 컬럼들이 자동으로 객체에 매핑이 된다.")
    @Test
    void mapperTest() throws SQLException {
        String sql = selectQueryBuilder.toSql();

        Person person = executeQuery(sql);

        assertThat(person).isEqualTo(person1);
    }

    private Person executeQuery(final String sql) {
        try (final ResultSet resultSet = connection.prepareStatement(sql).executeQuery()) {
            resultSet.next();
            return repositoryMapper.mapper(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("SQL 에러가 발생하였습니다.");
        }
    }
}
