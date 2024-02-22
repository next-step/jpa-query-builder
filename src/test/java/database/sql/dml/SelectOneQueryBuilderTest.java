package database.sql.dml;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SelectOneQueryBuilderTest {
    private final SelectOneQueryBuilder selectOneQueryBuilder = new SelectOneQueryBuilder(Person4.class);

    @Test
    void buildSelectOneQuery() {
        String actual = selectOneQueryBuilder.buildQuery(1L);
        assertThat(actual).isEqualTo("SELECT id, nick_name, old, email FROM users WHERE id = 1");
    }
}
