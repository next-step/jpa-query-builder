package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.Person;
import persistence.sql.dialect.H2Dialect;

import static org.assertj.core.api.Assertions.assertThat;
import static persistence.sql.dml.BooleanExpression.eq;

class DMLQueryGeneratorTest {
    DMLQueryGenerator dmlQueryGenerator = new DMLQueryGenerator(Person.class, new H2Dialect());

    @Test
    @DisplayName("요구사항1: insert 쿼리 생성")
    void testGenerateInsertQuery() {
        String nickName = "nickName";
        int age = 20;
        String email = "email";
        Person person = new Person(null, nickName, age, email, null);
        String expected = String.format(
                "insert into users (nick_name, old, email) values ('%s', %s, '%s')",
                nickName,
                age,
                email
        );

        String insertQuery = dmlQueryGenerator.generateInsertQuery(person);

        assertThat(insertQuery).isEqualTo(expected);
    }

    @Test
    @DisplayName("요구사항2: findAll 쿼리 생성")
    void testFindAll() {
        String expected = "select * from users";
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        String selectQuery = dmlQueryGenerator.generateSelectQuery(booleanBuilder);

        assertThat(selectQuery).isEqualTo(expected);
    }

    @Test
    @DisplayName("요구사항3: findById 쿼리 생성")
    void testFindById() {
        int id = 1;
        String expected = String.format("select * from users where id = %s", id);
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(eq("id", id));
        String selectQuery = dmlQueryGenerator.generateSelectQuery(booleanBuilder);

        assertThat(selectQuery).isEqualTo(expected);
    }

    @Test
    @DisplayName("요구사항4: delete by id 쿼리 생성")
    void testDeleteById() {
        int id = 1;
        String expected = String.format("delete from users where id = %s", id);
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(eq("id", id));
        String selectQuery = dmlQueryGenerator.generateDeleteQuery(booleanBuilder);

        assertThat(selectQuery).isEqualTo(expected);
    }
}
