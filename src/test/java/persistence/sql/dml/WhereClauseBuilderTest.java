package persistence.sql.dml;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.exception.PersistenceException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WhereClauseBuilderTest {
    @Test
    @DisplayName("주어진 column 들을 이용해 Where Clause 을 생성 할 수 있다.")
    void buildTest() {
        final String query = WhereClauseBuilder.builder()
                .and("test", "2")
                .and("column", "1")
                .build();

        assertThat(query).isEqualToIgnoringCase(" where test=2 and column=1");
    }

    @Test
    @DisplayName("where 를 한꺼번에 넣을땐 columns 와 data 길이가 같아야한다.")
    void columnsValuesSizeNotSameTest() {
        assertThatThrownBy(() -> WhereClauseBuilder.builder()
                .and(List.of("test"), List.of("1", "2"))
                .build())
                .isInstanceOf(PersistenceException.class);
    }
}
