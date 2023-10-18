package persistence.dialect;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.exception.PersistenceException;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RownumPagingStrategyTest {

    private PagingStrategy pagingStrategy;

    @BeforeEach
    void setUp() {
        pagingStrategy = RownumPagingStrategy.getInstance();
    }

    @ParameterizedTest(name = "offst:{0} limit:{1}")
    @MethodSource("provideParameters")
    @DisplayName("RownumPagingStrategy 는 rownum 을 이용한 페이징 쿼리를 생성한다.")
    void rownumPagingStrategyStrategyOffsetZeroTest(final int offset, final int limit, final String expected) {
        final String query = pagingStrategy.renderPagingQuery("select id, age, email from user", offset, limit);

        assertThat(query).isEqualToIgnoringCase(expected);
    }

    @Test
    @DisplayName("offset 은 0보다 작을 수 없다.")
    void offsetValidationTest() {
        assertThatThrownBy(()->pagingStrategy.renderPagingQuery("select id, age, email from user", -1, 0))
                .isInstanceOf(PersistenceException.class);
    }

    @Test
    @DisplayName("offset 은 0보다 작을 수 없다.")
    void limitValidationTest() {
        assertThatThrownBy(()->pagingStrategy.renderPagingQuery("select id, age, email from user", 0, -1))
                .isInstanceOf(PersistenceException.class);
    }

    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of(0, 10,"select * from (select row.*, rownum as rnum from (select id, age, email from user) row) where rnum > 0 and rnum <= 10"),
                Arguments.of(10, 20,"select * from (select row.*, rownum as rnum from (select id, age, email from user) row) where rnum > 10 and rnum <= 30"),
                Arguments.of(0, 100,"select * from (select row.*, rownum as rnum from (select id, age, email from user) row) where rnum > 0 and rnum <= 100"),
                Arguments.of(100, 200,"select * from (select row.*, rownum as rnum from (select id, age, email from user) row) where rnum > 100 and rnum <= 300")
        );
    }

}
