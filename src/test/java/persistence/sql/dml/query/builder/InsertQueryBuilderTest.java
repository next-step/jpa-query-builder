package persistence.sql.dml.query.builder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;

import static org.assertj.core.api.Assertions.assertThat;

class InsertQueryBuilderTest {

    @DisplayName("Person 테이블에 들어갈 Insert 쿼리를 반환한다.")
    @Test
    void insertQueryBuild() {
        Person person = new Person(1L, "신성수", 20, "tlstjdtn@nextstep.com");

        String sql = InsertQueryBuilder.from(person).toSql();

        assertThat(sql).isEqualTo("INSERT INTO Person (id,nick_name,old,email) VALUES (1,'신성수',20,'tlstjdtn@nextstep.com')");
    }
}
