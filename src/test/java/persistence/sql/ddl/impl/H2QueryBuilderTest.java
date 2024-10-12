package persistence.sql.ddl.impl;

import jakarta.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.config.PersistenceConfig;
import persistence.sql.ddl.node.EntityNode;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("H2 Query Builder Test")
class H2QueryBuilderTest {
    private H2QueryBuilder queryBuilder;

    @BeforeEach
    void setUp() {
        PersistenceConfig persistenceConfig = PersistenceConfig.getInstance();
        queryBuilder = (H2QueryBuilder) persistenceConfig.queryBuilder();
    }

    @Test
    @DisplayName("buildCreateTableQuery 는 매개변수 클래스를 스네이크 케이스 기반으로 생성 쿼리를 반환한다.")
    void buildCreateTableQuery() {
        EntityNode<?> entityNode = EntityNode.from(CamelCaseTable.class);

        // given
        String expected = "CREATE TABLE camel_case_table (sample_id BIGINT NOT NULL , name VARCHAR(255) , age INTEGER , PRIMARY KEY (sample_id));";
        // when
        String actual = queryBuilder.buildCreateTableQuery(entityNode);
        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("buildCreateTableQuery 는 매개변수 클래스를 내의 GeneratedValue 애노테이션에 따라 생성 쿼리를 반환한다.")
    void buildCreateTableQueryGeneratedValue() {
        EntityNode<?> entityNode = EntityNode.from(PersonV2.class);

        // given
        String expected = "CREATE TABLE person_v2 (id BIGINT AUTO_INCREMENT NOT NULL, nick_name VARCHAR(255) , old INTEGER , email VARCHAR(255) NOT NULL, PRIMARY KEY (id));";
        // when
        String actual = queryBuilder.buildCreateTableQuery(entityNode);
        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Entity
    private static class CamelCaseTable {
        @Id
        private Long sampleId;
        private String name;
        private Integer age;
    }

    @Entity
    public class PersonV2 {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "nick_name")
        private String name;

        @Column(name = "old")
        private Integer age;

        @Column(nullable = false)
        private String email;

    }

}