package persistence.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.domain.FixtureEntity;
import persistence.exception.ColumnNotExistException;

import static org.assertj.core.api.Assertions.*;

class EntityColumnsTest {

    private Class<?> mockClass;

    @Test
    @DisplayName("Id 컬럼 정보가 있는 클래스를 이용해 EntityColumns 인스턴스를 생성할 수 있다.")
    void entityColumnsCreateTest() {
        mockClass = FixtureEntity.WithId.class;
        final EntityColumns columns = new EntityColumns(mockClass);
        assertThat(columns).isNotNull();
    }

    @Test
    @DisplayName("Id 컬럼 정보가 존재하지 않으면 EntityColumns 인스턴스 생성에 실패해야한다.")
    void entityColumnsCreateFailureTest() {
        mockClass = FixtureEntity.WithoutId.class;
        assertThatThrownBy(()->new EntityColumns(mockClass))
                .isInstanceOf(ColumnNotExistException.class);
    }

    @Test
    @DisplayName("EntityColumns.getId 메서드를 통해 Id 컬럼을 가져올 수 있어야 한다.")
    void entityColumnsGetIdTest() throws Exception {
        mockClass = FixtureEntity.WithId.class;
        final EntityColumns columns = new EntityColumns(mockClass);
        final EntityColumn idColumn = new EntityColumn(mockClass.getDeclaredField("id"));
        assertThat(columns.getId()).isEqualTo(idColumn);
    }

    @Test
    @DisplayName("@Transient 를 가진 필드는 EntityColumns 에 포함되지 않는다.")
    void entityColumnsWithTransientNotExistTest() throws Exception {
        mockClass = FixtureEntity.WithTransient.class;
        final EntityColumns columns = new EntityColumns(mockClass);
        final EntityColumn idColumn = new EntityColumn(mockClass.getDeclaredField("column"));
        assertThatIterable(columns).doesNotContain(idColumn);
    }

}
