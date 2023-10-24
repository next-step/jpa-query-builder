package persistence.sql.dml.builder;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.mock.MockEntity;
import persistence.sql.meta.MetaFactory;

import static org.assertj.core.api.Assertions.assertThat;

class WhereClauseBuilderTest {

    @Test
    @DisplayName("조건값이 없을 때 WHERE 조건은 빌드되지 않음")
    void emptyCondition() {
        WhereClauseBuilder builder = WhereClauseBuilder.builder(MetaFactory.get(Person.class));
        String whereClause = builder.build();
        assertThat(whereClause).isEmpty();
    }

    @Test
    @DisplayName("PK 조건값이 존재할 때")
    void pkCondition() {
        WhereClauseBuilder builder = WhereClauseBuilder.builder(MetaFactory.get(Person.class));
        String whereClause = builder.buildPkClause(1L);
        assertThat(whereClause).isEqualTo(" WHERE id=1");
    }

    @Test
    @DisplayName("PK 조건값 append")
    void appendPkClause() {
        MockEntity 테스트 = new MockEntity(2L, "테스트");
        WhereClauseBuilder builder = WhereClauseBuilder.builder(MetaFactory.get(MockEntity.class));
        builder.appendPkClause(테스트);
        String whereClause = builder.build();
        assertThat(whereClause).isEqualTo(" WHERE id=2");
    }
}
