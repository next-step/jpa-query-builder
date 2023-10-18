package persistence.dialect;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.exception.PersistenceException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class DefaultPagingStrategyTest {

    private PagingStrategy pagingStrategy;

    @BeforeEach
    void setUp() {
        pagingStrategy = DefaultPagingStrategy.getInstance();
    }

    @Test
    @DisplayName("DefaultPagingStrategy 는 offset 이 0 인 경우 limit 만 쿼리에 붙인다.")
    void defaultPagingStrategyOffsetZeroTest() {
        final String query = pagingStrategy.renderPagingQuery("select id, age, email from user", 0, 10);

        assertThat(query).isEqualToIgnoringCase("select id, age, email from user limit 10");
    }

    @Test
    @DisplayName("DefaultPagingStrategy 는 offset 이 0 이 아닌 경우 limit ? offset ? 쿼리에 붙인다.")
    void defaultPagingStrategyWithOffsetTest() {
        final String query = pagingStrategy.renderPagingQuery("select id, age, email from user", 1, 10);

        assertThat(query).isEqualToIgnoringCase("select id, age, email from user limit 10 offset 1");
    }

    @Test
    @DisplayName("offset 은 0보다 작을 수 없다.")
    void offsetValidationTest() {
        assertThatThrownBy(() -> pagingStrategy.renderPagingQuery("select id, age, email from user", -1, 0))
                .isInstanceOf(PersistenceException.class);
    }

    @Test
    @DisplayName("offset 은 0보다 작을 수 없다.")
    void limitValidationTest() {
        assertThatThrownBy(() -> pagingStrategy.renderPagingQuery("select id, age, email from user", 0, -1))
                .isInstanceOf(PersistenceException.class);
    }
}
