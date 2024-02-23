package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dml.entity.Person;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DeleteQueryBuilderTest {

    @Test
    @DisplayName("Person 객체를 이용한 DML DELETE 생성 테스트")
    void DMLDeleteTest() {
        // given
        String expectedQuery = "DELETE FROM USERS WHERE ID = 1;";

        // when
        String actualQuery = new DeleteQueryBuilder(Person.class, List.of("id"), List.of(1L), new ArrayList<>()).build();

        // then
        assertThat(actualQuery).isEqualTo(expectedQuery);
    }
}
