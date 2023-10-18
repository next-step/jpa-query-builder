package persistence.sql.dml;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.exception.PersistenceException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DeleteQueryBuilderTest {
    @Test
    @DisplayName("주어진 정보를 이용해 Delete Query 을 생성 할 수 있다.")
    void buildTest() {
        final String query = DeleteQueryBuilder.builder()
                .table("test")
                .where("column", "1")
                .build();
        assertThat(query).isEqualToIgnoringCase("delete from test where column=1");
    }

    @Test
    @DisplayName("TableName 없이 build 할 수 없다.")
    void noTableNameBuildFailureTest() {
        assertThatThrownBy(() -> DeleteQueryBuilder.builder()
                .build())
                .isInstanceOf(PersistenceException.class);
    }
    @Test
    @DisplayName("where 를 한꺼번에 넣을땐 columns 와 data 길이가 같아야한다.")
    void columnsValuesSizeNotSameTest() {
        assertThatThrownBy(() -> DeleteQueryBuilder.builder()
                .where(List.of("test"), List.of("1", "2"))
                .build())
                .isInstanceOf(PersistenceException.class);
    }

}
