package persistence.sql.dml;

import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.dbms.DbmsStrategy;

import static org.assertj.core.api.Assertions.*;

class SelectDMLQueryBuilderTest {

    @Test
    void build() {
        SelectDMLQueryBuilder<Person> selectDMLQueryBuilder = new SelectDMLQueryBuilder<>(DbmsStrategy.H2, Person.class, SelectQuery.select());
        assertThat(selectDMLQueryBuilder.build()).isEqualTo("SELECT * \n" +
                " FROM USERS \n" +
                "  ;");
    }
}
