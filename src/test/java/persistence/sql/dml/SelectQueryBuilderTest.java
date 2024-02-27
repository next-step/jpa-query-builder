package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.Person;

import static org.assertj.core.api.Assertions.assertThat;
import static persistence.sql.dml.BooleanExpression.eq;

class SelectQueryBuilderTest {
    SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(Person.class);

    @Test
    @DisplayName("요구사항2: findAll 쿼리 생성")
    void testFindAll() {
        String expected = "select * from users";
        WhereBuilder booleanBuilder = new WhereBuilder();
        String selectQuery = selectQueryBuilder.toQuery(booleanBuilder);

        assertThat(selectQuery).isEqualTo(expected);
    }

    @Test
    @DisplayName("요구사항3: findById 쿼리 생성")
    void testFindById() {
        int id = 1;
        String expected = String.format("select * from users where id = %s", id);
        WhereBuilder booleanBuilder = new WhereBuilder();
        booleanBuilder.and(eq("id", id));
        String selectQuery = selectQueryBuilder.toQuery(booleanBuilder);

        assertThat(selectQuery).isEqualTo(expected);
    }
}
