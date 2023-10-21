package persistence.sql.dml.builder;

import entity.Person;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DeleteQueryBuilderTest {
    @Test
    void test() {
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder();
        String dml = deleteQueryBuilder.prepareStatement(Person.class, String.valueOf(1));
        assertThat(dml).isEqualTo("DELETE * FROM Person where id = 1");
    }
}
