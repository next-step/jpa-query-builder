package persistence.sql.ddl.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.node.EntityNode;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("AnnotatedTableScanner Test")
class AnnotatedTableScannerTest {
    private final AnnotatedTableScanner annotatedTableScanner = new AnnotatedTableScanner();

    @Test
    @DisplayName("scan 함수는 Entity 어노테이션이 붙은 클래스를 찾아 EntityNode로 변환한다.")
    void scanTes() {
        // given
        String basePackage = "persistence.sql.ddl.impl.fixture";

        // when
        Set<EntityNode<?>> actualSet = annotatedTableScanner.scan(basePackage);
        List<EntityNode<?>> actualList =  actualSet.stream().toList();

        // then
        assertThat(actualSet).hasSize(1);

        Class<?> entityClass = actualList.getFirst().entityClass();
        assertThat(entityClass.getSimpleName()).isEqualTo("ExampleClassOne");
    }

    @Test
    @DisplayName("scan 함수는 Entity 애노테이션이 붙은 클래스가 없을 경우 빈 Set을 반환한다.")
    void scanTestWhenNoEntity() {
        // given
        String basePackage = "jdbc";

        // when
        Set<EntityNode<?>> actualSet = annotatedTableScanner.scan(basePackage);

        // then
        assertThat(actualSet).isEmpty();
    }

}