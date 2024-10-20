package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.exception.ExceptionMessage;
import persistence.sql.exception.RequiredClassException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Drop_Table_Builder 테스트")
class H2DropQueryBuilderTest {

    @Test
    void Entity_테이블_삭제_쿼리_가져오기() {
        QueryBuilder dropQueryBuilder = new DropQueryBuilder(Person.class);
        String sql = dropQueryBuilder.build();
        assertThat(sql).isEqualTo("DROP TABLE IF EXISTS users");
    }

    @Test
    void 객체_생성시_클래스_Null_일경우_예외_발생() {
        assertThatThrownBy(() -> new DropQueryBuilder(null))
                .isInstanceOf(RequiredClassException.class)
                .hasMessage(ExceptionMessage.REQUIRED_CLASS.getMessage());
    }

}
