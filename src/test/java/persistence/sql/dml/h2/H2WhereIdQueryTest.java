package persistence.sql.dml.h2;

import domain.Person;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class H2WhereIdQueryTest {

    @Test
    @DisplayName("id 로 조건을 거는 WHERE 문을 만들어 낸다.")
    void whereId() {
        Assertions.assertThat(
                H2WhereIdQuery.build(Person.class, 1)
        ).isEqualTo(" WHERE id = 1");
    }
}
