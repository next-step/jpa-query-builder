package persistence.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.fixture.EntityWithoutDefaultConstructor;

import static org.assertj.core.api.Assertions.*;

class InstanceFactoryTest {
    @DisplayName("인스턴스를 생성한다.")
    @Test
    void createInstance() {
        // given
        final InstanceFactory<EntityWithoutDefaultConstructor> instanceFactory = new InstanceFactory<>(EntityWithoutDefaultConstructor.class);

        // when & then
        assertThatThrownBy(instanceFactory::createInstance)
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining(InstanceFactory.NO_DEFAULT_CONSTRUCTOR_FAILED_MESSAGE);
    }
}
