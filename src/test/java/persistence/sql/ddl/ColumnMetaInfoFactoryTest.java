package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;

import java.lang.reflect.Field;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ColumnMetaInfoFactoryTest {

    @Test
    @DisplayName("지원 하는 Annotation 테스트")
    public void supportedAnnotationTest() throws NoSuchFieldException {
        Field field = Person.class.getDeclaredField("id");

        List<ColumnMetaInfo> columnMetaInfos = ColumnMetaInfoFactory.createColumnMetaInfo(field);

        assertThat(columnMetaInfos.get(0).getValue()).isEqualTo("AUTO_INCREMENT");
        assertThat(columnMetaInfos.get(1).getValue()).isEqualTo("PRIMARY KEY");
    }

    @Test
    @DisplayName("지원 하지 않는 Annotation 테스트")
    public void notSupportedAnnotationTest() throws NoSuchFieldException {
        Field field = notSupportedAnnotation.class.getDeclaredField("test");

        assertThatThrownBy(() -> ColumnMetaInfoFactory.createColumnMetaInfo(field))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지원하지 않는 어노테이션입니다.");
    }

    private class notSupportedAnnotation {

        @Deprecated
        private String test;

    }

}
