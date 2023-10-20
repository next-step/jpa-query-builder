package persistence.sql.dml;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.dialect.Dialect;
import persistence.dialect.h2.H2Dialect;
import persistence.dialect.oracle.OracleDialect;
import persistence.exception.PersistenceException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SelectQueryBuilderTest {

    private Dialect dialect;

    @BeforeEach
    void setUp() {
        this.dialect = new H2Dialect();
    }

    @Test
    @DisplayName("주어진 column 들을 이용해 Select Query 을 생성 할 수 있다.")
    void buildTest() {
        final String query = new SelectQueryBuilder(dialect)
                .table("test")
                .column("column")
                .column("column2")
                .where("column", "1")
                .build();

        assertThat(query).isEqualToIgnoringCase("select column, column2 from test where column=1");
    }

    @Test
    @DisplayName("주어진 column 들을 이용해 페이징 limitOffset Select Query 을 생성 할 수 있다.")
    void limitOffsetPagingQueryTest() {
        final String limitQuery = new SelectQueryBuilder(dialect)
                .table("test")
                .column("column")
                .column("column2")
                .where("column", "1")
                .limit(0)
                .offset(10)
                .build();

        assertThat(limitQuery)
                .isEqualToIgnoringCase("select column, column2 from test where column=1 limit 0 offset 10");
    }

    @Test
    @DisplayName("주어진 column 들을 이용해 페이징 rownum Select Query 을 생성 할 수 있다.")
    void rownumPagingQueryTest() {
        final Dialect oracleDialect = new OracleDialect();
        final String rownumQuery = new SelectQueryBuilder(oracleDialect)
                .table("test")
                .column("column")
                .column("column2")
                .where("column", "1")
                .limit(0)
                .offset(10)
                .build();

        assertThat(rownumQuery)
                .isEqualToIgnoringCase("select * from (select row.*, rownum as rnum from (select column, column2 from test where column=1) row) where rnum > 10 and rnum <= 10");
    }

    @Test
    @DisplayName("TableName 없이 build 할 수 없다.")
    void noTableNameBuildFailureTest() {
        assertThatThrownBy(() -> new SelectQueryBuilder(dialect)
                .column("test")
                .build())
                .isInstanceOf(PersistenceException.class);
    }

    @Test
    @DisplayName("주어진 column 없이 build 할 수 없다.")
    void noDataBuildFailureTest() {
        assertThatThrownBy(() -> new SelectQueryBuilder(dialect)
                .table("test")
                .build())
                .isInstanceOf(PersistenceException.class);
    }

    @Test
    @DisplayName("where 를 한꺼번에 넣을땐 columns 와 data 길이가 같아야한다.")
    void columnsValuesSizeNotSameTest() {
        assertThatThrownBy(() -> new SelectQueryBuilder(dialect)
                .where(List.of("test"), List.of("1", "2"))
                .build())
                .isInstanceOf(PersistenceException.class);
    }
}
