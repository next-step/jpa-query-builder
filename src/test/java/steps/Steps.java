package steps;

import jdbc.JdbcTemplate;
import orm.dsl.QueryBuilder;
import persistence.sql.ddl.Person;

public class Steps {

    public static <T> void 테이블_생성(JdbcTemplate jdbcTemplate, Class<T> entityClass) {
        QueryBuilder queryBuilder = new QueryBuilder(jdbcTemplate);
        queryBuilder.createTable(entityClass)
                .execute();
    }

    public static <T> void Person_엔티티_생성(JdbcTemplate jdbcTemplate, Person person) {
        QueryBuilder queryBuilder = new QueryBuilder(jdbcTemplate);
        queryBuilder.insertInto(Person.class)
                .value(person)
                .execute();

    }
}
