package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Create_Query_Builder 테스트")
class H2CreateQueryBuilderTest {

    @Test
    void Entity_테이블_생성_쿼리_가져오기() {
        CreateQueryBuilder createQueryBuilder = new H2CreateQueryBuilder(Person.class);
        String sql = createQueryBuilder.makeQuery();
        assertThat(sql).isEqualTo("CREATE TABLE users(id BIGINT PRIMARY KEY AUTO_INCREMENT,name VARCHAR(255) NULL,age INTEGER NULL,email VARCHAR(255) NOT NULL)");
    }

    @Test
    void 객체_생성시_클래스_NULL_일경우_예외_발생() {
        assertThatThrownBy(() -> new H2CreateQueryBuilder(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("class가 존재하지 않습니다.");
    }
}
