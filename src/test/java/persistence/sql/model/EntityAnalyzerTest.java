package persistence.sql.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.study.sql.ddl.Person1;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class EntityAnalyzerTest {

    private final EntityAnalyzer analyzer = new EntityAnalyzer(Person1.class);

    @Test
    @DisplayName("person1의 테이블 이름 분석하기")
    void analyzeTableName() {
        String tableName = analyzer.analyzeTableName();
        assertThat(tableName).isEqualTo("person1");
    }

    @Test
    @DisplayName("person1의 컬럼 분석하기")
    void analyzeColumns() {
        List<Column> columns = analyzer.analyzeColumns();

        Column id = new Column(SqlType.BIGINT, "id", List.of(SqlConstraint.PRIMARY_KEY));
        Column name = new Column(SqlType.VARCHAR, "name", List.of());
        Column age = new Column(SqlType.INTEGER, "age", List.of());

        assertSoftly(softly -> {
            softly.assertThat(columns).hasSize(3);
            softly.assertThat(columns).contains(id, name, age);
        });
    }
}
