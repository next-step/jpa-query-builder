package persistence.entity;

import jdbc.JdbcTemplate;
import persistence.Person;
import persistence.sql.dialect.Dialect;
import persistence.sql.dml.BooleanBuilder;
import persistence.sql.dml.BooleanExpression;
import persistence.sql.dml.DMLQueryGenerator;

public class EntityMangerImpl implements EntityManger {
    private JdbcTemplate jdbcTemplate;
    private Dialect dialect;

    public EntityMangerImpl(Dialect dialect, JdbcTemplate jdbcTemplate) {
        this.dialect = dialect;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Person find(Class<Person> clazz, Long id) {
        DMLQueryGenerator dmlQueryGenerator = new DMLQueryGenerator(clazz, dialect);
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(BooleanExpression.eq("id", id));
        String query = dmlQueryGenerator.generateSelectQuery(builder);

        return jdbcTemplate.queryForObject(query, rs ->
                new Person(
                        rs.getLong("id"),
                        rs.getString("nick_name"),
                        rs.getInt("old"),
                        rs.getString("email"),
                        null
                )
        );
    }

    @Override
    public Object persist(Object entity) {
        return null;
    }

    @Override
    public void remove(Object entity) {

    }
}
