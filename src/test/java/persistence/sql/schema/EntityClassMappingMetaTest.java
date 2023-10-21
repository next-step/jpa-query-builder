package persistence.sql.schema;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dialect.H2ColumnType;
import persistence.sql.exception.RequiredAnnotationException;

@DisplayName("EntityMeta 테스트")
class EntityClassMappingMetaTest {

    @Test
    @DisplayName("@Entity 어노테이션은 필수입니다.")
    void requireEntityAnnotation() {
        assertThatThrownBy(
            () -> EntityClassMappingMeta.of(EntityMappingMetaFixtureWithNoEntityAnnotation.class, new H2ColumnType())
        )
            .isInstanceOf(RequiredAnnotationException.class)
            .hasMessage("@Entity annotation is required");
    }

    @Test
    @DisplayName("Entity의 클래스명의 대문자와 Table명은 매칭됩니다.")
    void matchEntityClassNameUppercaseIsMatchedWithTableName() {
        final EntityClassMappingMeta entityClassMappingMeta = EntityClassMappingMeta.of(EntityMappingMetaFixture.class, new H2ColumnType());
        final String tableClause = entityClassMappingMeta.tableClause();

        assertThat(tableClause).isEqualTo(EntityMappingMetaFixture.class.getSimpleName().toUpperCase());
    }

    @Test
    @DisplayName("Entity의 필드명의 소문자와 타입은 Table 컬럼명과 타입, 제약조건에 매칭됩니다.")
    void matchEntityFieldNameLowercaseIsMatchedWithFieldName() {
        final EntityClassMappingMeta entityClassMappingMeta = EntityClassMappingMeta.of(EntityMappingMetaFixture.class, new H2ColumnType());
        final String fieldClause = entityClassMappingMeta.fieldClause();

        assertThat(fieldClause).isEqualTo("id bigint PRIMARY KEY, name varchar, index integer");
    }

    static class EntityMappingMetaFixtureWithNoEntityAnnotation {

        private Long id;
    }

    @Entity
    static class EntityMappingMetaFixture {

        @Id
        private Long id;

        private String name;

        private Integer index;
    }
}