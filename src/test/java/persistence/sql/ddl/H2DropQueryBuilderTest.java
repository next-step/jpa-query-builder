package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Drop_Table_Builder 테스트")
class H2DropQueryBuilderTest {

    @Test
    void Entity_테이블_삭제_쿼리_가져오기() {
        DropQueryBuilder dropQueryBuilder = new H2DropQueryBuilder(Person.class);
        String sql = dropQueryBuilder.makeQuery();
        assertThat(sql).isEqualTo("DROP TABLE IF EXISTS users");
    }

    @Test
    void 객체_생성시_클래스_Null_일경우_예외_발생() {
        assertThatThrownBy(() -> new H2DropQueryBuilder(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("class가 존재하지 않습니다.");
    }

}
