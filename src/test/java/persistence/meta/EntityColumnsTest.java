package persistence.meta;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.exception.FieldEmptyException;
import persistence.testFixtures.Person;

class EntityColumnsTest {

    @Test
    @DisplayName("필드 없으면 예외가 발생한다.")
    void empty() {
        assertThatExceptionOfType(FieldEmptyException.class)
                .isThrownBy(() -> new EntityColumns(null));

    }

    @Test
    @DisplayName("리플랙션 필드에 의해 칼럼들이 생성이 된다")
    void createQuery() {
        //when
        EntityColumns entityColumns = new EntityColumns(Person.class.getDeclaredFields());

        final List<EntityColumn> entityColumnsList = entityColumns.getEntityColumns();

        assertThat(entityColumnsList).hasSize(4);
    }
}
