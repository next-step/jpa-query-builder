package persistence.sql.dml.builder;

import entity.Person;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SelectInsertQueryBuilderTest {
    @Test
    void test() {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String dml = selectQueryBuilder.prepareStatement(Person.class, String.valueOf(1));
        assertThat(dml).isEqualTo("SELECT * FROM Person where id = 1");
    }
}
