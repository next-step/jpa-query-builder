package persistence.sql.dml;

import domain.Person;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("DeleteQueryBuilder class 의")
class DeleteQueryBuilderTest {

    @DisplayName("generateQuery 메서드는")
    @Nested
    class GenerateQuery {

        @DisplayName("Person Entity의 delete 쿼리가 만들어지는지 확인한다.")
        @Test
        void testGenerateQuery() {
            // given
            DeleteQueryBuilder deleteQueryBuilder = DeleteQueryBuilder.from();
            Class<?> clazz = Person.class;

            // when
            String query = deleteQueryBuilder.generateQuery(clazz);

            // then
            assertEquals("DELETE FROM users", query);
        }
    }
}
