package persistence.sql.dml.column;

import fixture.PersonV3;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DmlColumnsTest {
    private final PersonV3 target = new PersonV3(
            null,
            "yohan",
            31,
            "yohan@google.com",
            1
    );


    @Test
    @DisplayName("@Id와 @Transient 어노테이션이 설정된 컬럼을 제외한 목록을 만든다")
    public void of() throws NoSuchFieldException {
        DmlColumns dmlColumns = DmlColumns.of(target);
        Class<? extends PersonV3> clazz = target.getClass();
        DmlColumns expected = new DmlColumns(
                List.of(
                        new DmlColumn(clazz.getDeclaredField("name"), target),
                        new DmlColumn(clazz.getDeclaredField("age"), target),
                        new DmlColumn(clazz.getDeclaredField("email"), target)
                )
        );

        assertThat(dmlColumns).isEqualTo(expected);
    }

    @Test
    @DisplayName("컬럼들의 이름 목록을 반환한다")
    public void columnTypes() {
        DmlColumns dmlColumns = DmlColumns.of(target);
        String types = dmlColumns.getNames();

        assertThat(types).isEqualTo("nick_name, old, email");
    }

    @Test
    @DisplayName("컬럼들의 값 목록을 반환한다")
    public void columValues() {
        DmlColumns dmlColumns = DmlColumns.of(target);
        String values = dmlColumns.getValues();

        assertThat(values).isEqualTo("'yohan', 31, 'yohan@google.com'");
    }
}