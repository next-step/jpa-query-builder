package persistence.sql.dml;

import database.DatabaseServer;
import database.H2;
import persistence.sql.common.DtoMapper;
import jdbc.JdbcTemplate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.entity.notcolumn.Person;
import persistence.sql.ddl.CreateQueryBuilder;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

import static persistence.sql.ddl.common.TestSqlConstant.DROP_TABLE;
import static persistence.sql.dml.TestFixture.*;

class DeleteQueryBuilderTest {

    @DisplayName("[요구사항4.1] Delete All 쿼리를 반환한다.")
    @Test
    void 요구사항2_test() {
        // given
        String expected = "DELETE FROM users";

        // when
        String actual = new DeleteQueryBuilder(Person.class).deleteAll();

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("[요구사항4.2] Delete by Id 쿼리를 반환한다.")
    @Test
    void 요구사항3_test() {
        // given
        Long id = 1L;

        // when
        String query = new DeleteQueryBuilder(Person.class).deleteById(id);

        // then
        Assertions.assertThat(query).isEqualTo(String.format("DELETE FROM users where id = %d", id));
    }
}