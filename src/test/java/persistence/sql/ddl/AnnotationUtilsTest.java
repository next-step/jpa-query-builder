package persistence.sql.ddl;

import jakarta.persistence.Column;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatRuntimeException;

class AnnotationUtilsTest {
    @Test
    void 애노테이션의_기본_값을_가져올_수_있다() {
        Object result = AnnotationUtils.getDefaultValue(Column.class, "length");

        assertThat(result).isEqualTo(255);
    }

    @Test
    void 애노테이션에_없는_값의_경우_실패한다() {
        assertThatRuntimeException()
                .isThrownBy(() -> AnnotationUtils.getDefaultValue(Column.class, "fake"));
    }
}
