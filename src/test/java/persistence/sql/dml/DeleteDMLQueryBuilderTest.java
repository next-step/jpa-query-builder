package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.dbms.DbmsStrategy;
import persistence.sql.dml.clause.WhereClause;
import persistence.sql.dml.clause.operator.Operator;

import static org.assertj.core.api.Assertions.assertThat;

class DeleteDMLQueryBuilderTest {

    @DisplayName("DELETE 쿼리 생성")
    @Test
    void builder() {
        DeleteDMLQueryBuilder<Person> deleteDMLQueryBuilder = new DeleteDMLQueryBuilder<>(DbmsStrategy.H2, Person.class)
                .where(WhereClause.of("ID", 1L, Operator.EQUALS));

        assertThat(deleteDMLQueryBuilder.build()).isEqualTo(
                "DELETE FROM" +
                        " USERS \n " +
                        " WHERE ID = 1" +
                        ";");
    }
}
