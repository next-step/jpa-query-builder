package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ColumnOptionFactoryTest {

    @Test
    @DisplayName("지원 하는 Annotation 테스트")
    public void supportedAnnotationTest() throws NoSuchFieldException {
        Field field = Person.class.getDeclaredField("id");

        String columnOptions = ColumnOptionFactory.createColumnOption(field);

        assertThat(columnOptions).isEqualTo("AUTO_INCREMENT PRIMARY KEY");
    }

    @Test
    @DisplayName("지원 하지 않는 Annotation 테스트")
    public void notSupportedAnnotationTest() throws NoSuchFieldException {
        Field field = notSupportedAnnotation.class.getDeclaredField("test");

        assertThatThrownBy(() -> ColumnOptionFactory.createColumnOption(field))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당하는 AnnotationHandler가 없습니다.");
    }

    private class notSupportedAnnotation {

        @Deprecated
        private String test;

    }

}
