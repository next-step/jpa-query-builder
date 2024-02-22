package util;

import org.junit.jupiter.api.Test;
import persistence.sql.model.Column;
import persistence.study.sql.ddl.Person1;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class EntityAnalyzerTest {

    @Test
    void getTableName() {
        String result = EntityAnalyzer.getTableName(Person1.class);

        assertThat(result).isEqualTo("person1");
    }

    @Test
    void getColumns() {
        List<Column> columns = EntityAnalyzer.getColumns(Person1.class);

        assertThat(columns.size()).isEqualTo(3);
    }
}
