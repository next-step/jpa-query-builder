package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.model.Table;
import persistence.study.sql.ddl.Person3;

import static org.assertj.core.api.Assertions.assertThat;

class DeleteQueryBuilderTest {

    @DisplayName("Person을 이용하여 delete 쿼리 생성하기")
    @Test
    void build() {
        Table table = new Table(Person3.class);
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(table);

        String result = deleteQueryBuilder.build();

        String deleteQuery = "DELETE FROM users;";
        assertThat(result).isEqualTo(deleteQuery);
    }
}
