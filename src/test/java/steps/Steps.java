package steps;

import jdbc.JdbcTemplate;
import orm.dsl.ddl.DDLQueryBuilder;
import orm.dsl.dml.DMLQueryBuilder;
import persistence.sql.ddl.Person;

public class Steps {

    public static <T> void 테이블_생성(JdbcTemplate jdbcTemplate, Class<T> entityClass) {
        DDLQueryBuilder ddlQueryBuilder = new DDLQueryBuilder(jdbcTemplate);
        ddlQueryBuilder.createTable(entityClass)
                .execute();
    }

    public static <T> void Person_엔티티_생성(JdbcTemplate jdbcTemplate, Person person) {
        DMLQueryBuilder dmlQueryBuilder = new DMLQueryBuilder(jdbcTemplate);
        dmlQueryBuilder.insertInto(Person.class)
                .values(person)
                .execute();

    }
}
