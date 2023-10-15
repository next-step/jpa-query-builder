package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EntityColumnsTest {

    private Class<?> mockClass;

    @Test
    @DisplayName("EntityColumns 생성 테스트")
    void entityColumnsCreateTest() {
        mockClass = MockEntity.WithId.class;
        final EntityColumns columns = new EntityColumns(mockClass);
        assertThat(columns).isNotNull();
    }

    @Test
    @DisplayName("EntityColumns 생성 실패 테스트")
    void entityColumnsCreateFailureTest() {
        mockClass = MockEntity.WithoutId.class;
        assertThatThrownBy(()->new EntityColumns(mockClass))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("EntityColumns getId 메서드 테스트")
    void entityColumnsGetIdTest() throws Exception {
        mockClass = MockEntity.WithId.class;
        final EntityColumns columns = new EntityColumns(mockClass);
        final EntityColumn idColumn = new EntityColumn(mockClass.getDeclaredField("id"));
        assertThat(columns.getId()).isEqualTo(idColumn);
    }

}