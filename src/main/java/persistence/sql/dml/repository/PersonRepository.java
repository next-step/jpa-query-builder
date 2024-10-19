package persistence.sql.dml.repository;

import java.sql.Connection;
import java.util.List;
import jdbc.JdbcTemplate;
import persistence.entity.Person;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.dml.query.builder.SelectQueryBuilder;

public class PersonRepository implements Repository<Person, Long> {

    private final JdbcTemplate jdbcTemplate;

    public PersonRepository(Connection connection) {
        this.jdbcTemplate = new JdbcTemplate(connection);
    }

    @Override
    public List<Person> findAll() {
        SelectQueryBuilder queryBuilder = new SelectQueryBuilder();
        String query = queryBuilder.build(Person.class, new H2Dialect());

        return jdbcTemplate.query(query, (resultSet -> new Person(
                resultSet.getLong("id"),
                resultSet.getString("nick_name"),
                resultSet.getInt("old"),
                resultSet.getString("email")
        )));
    }

    @Override
    public Person findById() {
        SelectQueryBuilder queryBuilder = new SelectQueryBuilder();
        String query = queryBuilder.build(Person.class, new H2Dialect());

        return jdbcTemplate.queryForObject(query, (resultSet -> new Person(
                resultSet.getLong("id"),
                resultSet.getString("nick_name"),
                resultSet.getInt("old"),
                resultSet.getString("email")
        )));
    }

}
