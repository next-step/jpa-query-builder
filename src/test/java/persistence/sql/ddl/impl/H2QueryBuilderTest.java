package persistence.sql.ddl.impl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.node.EntityNode;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("H2 Query Builder Test")
class H2QueryBuilderTest {
    private final H2QueryBuilder queryBuilder = new H2QueryBuilder();

    @Test
    @DisplayName("buildCreateTableQuery 는 매개변수 클래스를 스네이크 케이스 기반으로 생성 쿼리를 반환한다.")
    void buildCreateTableQuery() {
        EntityNode<?> entityNode = EntityNode.from(CamelCaseTable.class);

        // given
        String expected = "CREATE TABLE camel_case_table";
        // when
        String actual = queryBuilder.buildCreateTableQuery(entityNode);
        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("buildPrimaryKeyQuery 는 매개변수 필드 노드 리스트를 기반으로 PK 쿼리를 반환한다.")
    void buildPrimaryKeyQuery() {
        // given
        EntityNode<?> entityNode = EntityNode.from(CamelCaseTable.class);

        String expected = "PRIMARY KEY (sample_id)";
        // when
        String actual = queryBuilder.buildPrimaryKeyQuery(entityNode.getFields());
        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("buildColumnQuery 는 매개변수 필드 노드를 기반으로 컬럼 쿼리를 반환한다.")
    void buildColumnQuery() {
        // given
        EntityNode<?> entityNode = EntityNode.from(CamelCaseTable.class);

        List<String> expected = List.of(
                "sample_id BIGINT",
                "name VARCHAR(255)",
                "age INT"
        );
        // when
        List<String> actual = entityNode.getFields().stream()
                .map(queryBuilder::buildColumnQuery).toList();
        // then
        assertThat(actual).containsAll(expected);
    }

    @Entity
    private static class CamelCaseTable {
        @Id
        private Long sampleId;
        private String name;
        private Integer age;
    }
}