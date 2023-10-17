package persistence.sql.dml;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.exception.PersistenceException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InsertQueryBuilderTest {

    private InsertQueryBuilder insertQueryBuilder;

    @BeforeEach
    void setUp() {
        this.insertQueryBuilder = new InsertQueryBuilder();
    }

    @Test
    @DisplayName("주어진 data 를 이용해 Insert Query 을 생성 할 수 있다.")
    void buildTest() {
        final String query = insertQueryBuilder
                .table("test")
                .addData("id", "1")
                .addData("test", "test")
                .build();

        assertThat(query).isEqualToIgnoringCase("insert into test (id, test) values (1, test)");
    }

    @Test
    @DisplayName("TableName 없이 build 할 수 없다.")
    void noTableNameBuildFailureTest() {
        assertThatThrownBy(() -> insertQueryBuilder
                .addData("test", "test")
                .build())
                .isInstanceOf(PersistenceException.class);
    }

    @Test
    @DisplayName("주어진 data 없이 build 할 수 없다.")
    void noDataBuildFailureTest() {
        assertThatThrownBy(() -> insertQueryBuilder
                .table("test")
                .build())
                .isInstanceOf(PersistenceException.class);
    }

    @Test
    @DisplayName("data 를 한꺼번에 넣을땐 columns 와 values 길이가 같아야한다.")
    void columnsValuesSizeNotSameTest() {
        assertThatThrownBy(() -> insertQueryBuilder
                .addData(List.of("test"), List.of("1", "2"))
                .build())
                .isInstanceOf(PersistenceException.class);
    }

}
