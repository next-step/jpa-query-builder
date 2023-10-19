package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;

import static org.assertj.core.api.Assertions.assertThat;

class ColumnMetaInfoTest {

    @Test
    @DisplayName("지원 하지 않는 Annotation 테스트")
    public void notSupportedAnnotationTest() throws NoSuchFieldException {
        Deprecated deprecated = notSupportedAnnotation.class
                .getDeclaredField("test")
                .getAnnotation(Deprecated.class);

        ColumnMetaInfo columnMetaInfo = new ColumnMetaInfo(deprecated);

        assertThat(columnMetaInfo.getValue()).isEqualTo("");
    }

    private class notSupportedAnnotation {

        @Deprecated
        private String test;

    }

}
