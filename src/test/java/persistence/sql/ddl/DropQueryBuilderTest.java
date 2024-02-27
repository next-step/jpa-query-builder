package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DropQueryBuilderTest {
    @Test
    @DisplayName("요구사항4: 정보를 바탕으로 drop 쿼리 만들어보기")
    void testDropQueryGenerate() {
        DropQueryBuilder sut = new DropQueryBuilder(Requirement4.class);
        String expect = "drop table users";

        String query = sut.toQuery();

        assertThat(query.toLowerCase()).isEqualTo(expect.toLowerCase());
    }
}
