package persistence.entity;

import jdbc.JdbcTemplate;
import persistence.Person;
import persistence.sql.dialect.Dialect;
import persistence.sql.dml.*;

public class EntityMangerImpl implements EntityManger {
    private JdbcTemplate jdbcTemplate;

    public EntityMangerImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Person find(Class<Person> clazz, Long id) {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(clazz);
        WhereBuilder builder = new WhereBuilder();
        builder.and(BooleanExpression.eq("id", id));
        String query = selectQueryBuilder.toQuery(builder);

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
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(entity.getClass());
        jdbcTemplate.execute(insertQueryBuilder.toQuery(entity));
        Person person = (Person) entity;
        person.setId(1L);
        return entity;
    }

    @Override
    public void remove(Object entity) {
        Person person = (Person) entity;
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(entity.getClass());
        WhereBuilder builder = new WhereBuilder();
        builder.and(BooleanExpression.eq("id", person.getId()));
        jdbcTemplate.execute(deleteQueryBuilder.toQuery(builder));
    }
}
