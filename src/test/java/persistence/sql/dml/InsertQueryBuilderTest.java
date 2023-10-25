package persistence.sql.dml;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InsertQueryBuilderTest {


    @Test
    @DisplayName("insert 쿼리를 생성한다.")
    void insertTest() {
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder();

        Assertions.assertThat(insertQueryBuilder.create())
                .isEqualTo("insert into(id, nick_name, old, email) values(1, '지영', 28, jy@lim.com)");
    }
}