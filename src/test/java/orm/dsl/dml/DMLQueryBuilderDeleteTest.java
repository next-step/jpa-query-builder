package orm.dsl.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;

import static org.assertj.core.api.Assertions.assertThat;
import static orm.util.ConditionUtils.eq;

public class DMLQueryBuilderDeleteTest {

    @Test
    @DisplayName("DELETE 절 생성 테스트")
    void DML_DELETE_문_테스트() {
        // given
        DMLQueryBuilder dmlQueryBuilder = new DMLQueryBuilder();

        // when
        String query = dmlQueryBuilder.deleteFrom(Person.class)
                .build();

        // then
        assertThat(query).isEqualTo("DELETE FROM person");
    }

    @Test
    @DisplayName("DELETE 절 조건 포함 실행 테스트")
    void DML_DELETE_문_조건절_테스트() {
        // given
        DMLQueryBuilder dmlQueryBuilder = new DMLQueryBuilder();

        // when
        String query = dmlQueryBuilder.deleteFrom(Person.class)
                .where(
                    eq("id", 1L)
                        .and(eq("name", "설동민"))
                        .or(eq("age", 30))
                )
                .build();

        // then
        assertThat(query).isEqualTo("DELETE FROM person WHERE id = 1 AND name = '설동민' OR age = 30");
    }
}

