package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.Dialect;
import persistence.sql.H2Dialect;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Create_Query_Builder 테스트")
class H2CreateQueryBuilderTest {

    private static final Dialect dialect = new H2Dialect();

    @Test
    void Entity_테이블_생성_쿼리_가져오기() {
        QueryBuilder createQueryBuilder = new CreateQueryBuilder(Person.class, dialect);
        String sql = createQueryBuilder.build();
        assertThat(sql).isEqualTo("CREATE TABLE IF NOT EXISTS users(id BIGINT PRIMARY KEY AUTO_INCREMENT,nick_name VARCHAR(255) NULL,old INTEGER NULL,email VARCHAR(255) NOT NULL)");
    }

    @Test
    void 객체_생성시_클래스_NULL_일경우_예외_발생() {
        assertThatThrownBy(() -> new CreateQueryBuilder(null, dialect))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("class가 존재하지 않습니다.");
    }
}
