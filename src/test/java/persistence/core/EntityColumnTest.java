package persistence.core;

import entityloaderfixture.depth.DepthPersonFixtureEntity;
import jakarta.persistence.Column;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class EntityColumnTest {


    @DisplayName("DepthPerson Entity의 Name 필드의 정보를 토대로 EntityColumn를 생성한다")
    @Test
    void createEntityColumn() throws Exception {
        // given
        Field nameField = DepthPersonFixtureEntity.class.getDeclaredField("name");
        Column columnAnnotation = nameField.getAnnotation(Column.class);

        // when
        EntityColumn nameEntityColumn = new EntityColumn(nameField);

        // then
        assertAll(
                () -> assertThat(nameEntityColumn.getName()).isEqualTo(nameField.getName()),
                () -> assertThat(nameEntityColumn.getType()).isEqualTo(String.class),
                () -> assertThat(nameEntityColumn.isNullable()).isEqualTo(columnAnnotation.nullable())
        );
    }

}
