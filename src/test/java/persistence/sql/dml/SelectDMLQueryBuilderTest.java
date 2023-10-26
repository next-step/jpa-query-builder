package persistence.sql.dml;

import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.dbms.Dialect;

import static org.assertj.core.api.Assertions.assertThat;

class SelectDMLQueryBuilderTest {

    @Test
    void build() {
        SelectDMLQueryBuilder<Person> selectDMLQueryBuilder = new SelectDMLQueryBuilder<>(Dialect.H2, Person.class);
        assertThat(selectDMLQueryBuilder.build()).isEqualTo("SELECT * \n" +
                " FROM USERS \n" +
                "  ;");
    }
}
