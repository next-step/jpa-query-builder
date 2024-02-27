package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.Person;

import static org.assertj.core.api.Assertions.assertThat;
import static persistence.sql.dml.BooleanExpression.eq;

class DeleteQueryBuilderTest {
    DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(Person.class);

    @Test
    @DisplayName("요구사항4: delete by id 쿼리 생성")
    void testDeleteById() {
        int id = 1;
        String expected = String.format("delete from users where id = %s", id);
        WhereBuilder booleanBuilder = new WhereBuilder();
        booleanBuilder.and(eq("id", id));
        String selectQuery = deleteQueryBuilder.toQuery(booleanBuilder);

        assertThat(selectQuery).isEqualTo(expected);
    }
}
