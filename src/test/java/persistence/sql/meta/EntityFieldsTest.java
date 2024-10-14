package persistence.sql.meta;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.fixture.EntityWithId;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class EntityFieldsTest {
    @Test
    @DisplayName("인스턴스를 생성한다.")
    void constructor() {
        // when
        final EntityFields entityFields = new EntityFields(EntityWithId.class);

        // then
        final Field[] fields = EntityWithId.class.getDeclaredFields();
        assertAll(
                () -> assertThat(entityFields).isNotNull(),
                () -> assertThat(entityFields.getEntityFields()).hasSize(5),
                () -> assertThat(entityFields.getEntityFields()).containsExactly(
                        new EntityField(fields[0]),
                        new EntityField(fields[1]),
                        new EntityField(fields[2]),
                        new EntityField(fields[3]),
                        new EntityField(fields[4])
                )
        );
    }
}
