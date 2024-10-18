package persistence.sql.dml.repository;

import static persistence.sql.dml.query.DMLQueryBuilderRegistry.getQueryBuilder;
import static persistence.sql.dml.query.DMLType.SELECT;

import java.sql.Connection;
import java.util.List;
import jdbc.JdbcTemplate;
import persistence.sql.ddl.Person;
import persistence.sql.dml.query.SelectQueryBuilder;
import persistence.sql.dml.query.metadata.ColumnName;
import persistence.sql.dml.query.metadata.WhereCondition;

public class PersonRepository implements Repository<Person, Long> {

    private final JdbcTemplate jdbcTemplate;

    public PersonRepository(Connection connection) {
        this.jdbcTemplate = new JdbcTemplate(connection);
    }

    @Override
    public List<Person> findAll() {
        SelectQueryBuilder queryBuilder = (SelectQueryBuilder) getQueryBuilder(SELECT);
        String query = queryBuilder.build(Person.class);

        return jdbcTemplate.query(query, (resultSet -> new Person(
                resultSet.getLong("id"),
                resultSet.getString("nick_name"),
                resultSet.getInt("old"),
                resultSet.getString("email")
        )));
    }

    @Override
    public Person findById() {
        SelectQueryBuilder queryBuilder = (SelectQueryBuilder) getQueryBuilder(SELECT);
        String query = queryBuilder.build(
                Person.class,
                List.of(new WhereCondition(new ColumnName("id"), "="))
        );

        return jdbcTemplate.queryForObject(query, (resultSet -> new Person(
                resultSet.getLong("id"),
                resultSet.getString("nick_name"),
                resultSet.getInt("old"),
                resultSet.getString("email")
        )));
    }

}
