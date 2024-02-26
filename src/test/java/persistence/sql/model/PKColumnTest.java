package persistence.sql.model;

import jakarta.persistence.GenerationType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.study.sql.ddl.Person3;

import java.lang.reflect.Field;
import java.util.List;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class PKColumnTest {

    @Test
    @DisplayName("올바른 pk 컬럼이 생성되었는지 확인")
    void getPKColumn() throws NoSuchFieldException {
        Class<Person3> clazz = Person3.class;
        Field id = clazz.getDeclaredField("id");

        PKColumn result = new PKColumn(id);

        String name = result.getName();
        List<SqlConstraint> constraints = result.getConstraints();
        GenerationType generationType = result.getGenerationType().get();

        assertSoftly(softly -> {
            softly.assertThat(name).isEqualTo("id");
            softly.assertThat(constraints).isEqualTo(List.of(SqlConstraint.PRIMARY_KEY));
            softly.assertThat(generationType).isEqualTo(GenerationType.IDENTITY);
        });
    }
}
