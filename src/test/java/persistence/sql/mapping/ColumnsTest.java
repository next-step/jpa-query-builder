package persistence.sql.mapping;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.Person;
import persistence.sql.ddl.exception.AnnotationMissingException;
import persistence.sql.ddl.exception.IdAnnotationMissingException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ColumnsTest {
    private final Person person = new Person(1L, "test", 10, "test", null);

    @Test
    @DisplayName("기본키 없으면 에러")
    void throwErrorWhenPrimaryKeyIsNotDefined() {
        assertThrows(IdAnnotationMissingException.class, () -> {
            Columns.createColumns(NoPrimaryKeyTest.class);
        });
    }

    @Test
    @DisplayName("Entity 클래스가 아니면 에러")
    void throwErrorWhenClassIsNotForEntity() {
        assertThrows(AnnotationMissingException.class, () -> {
            Columns.createColumns(NoEntityAnnotationTest.class);
        });
    }

    @Test
    @DisplayName("값 없이 생성 테스트")
    void testCreateColumns() {
        Columns columns = Columns.createColumns(Person.class);
        assertThat(columns).isNotEmpty();
    }

    @Test
    @DisplayName("값 넣으면서 생성 테스트")
    void testCreateWithValueColumns() {
        Columns columns = Columns.createColumnsWithValue(Person.class, person);

        assertSoftly(softly -> {
            assertThat(columns.getValues()).isNotEmpty();
            assertThat(columns.getValues().stream().noneMatch(Objects::isNull)).isTrue();
        });
    }

    @Test
    public void testGetNames() {
        Columns columns = Columns.createColumns(Person.class);

        List<String> names = columns.getNames();

        assertThat(names).containsExactly("nick_name", "old", "email");
    }

    @Test
    public void testGetValue() {
        Columns columns = Columns.createColumnsWithValue(Person.class, person);

        List<Object> names = columns.getValues();

        assertSoftly(softly -> {
            softly.assertThat(names.get(0)).isEqualTo(person.getName());
            softly.assertThat(names.get(1)).isEqualTo(person.getAge());
            softly.assertThat(names.get(2)).isEqualTo(person.getEmail());
        });
    }

    @Test
    public void testGetKeyColumns() {
        Columns columns = Columns.createColumns(Person.class);
        ColumnData foundColumn = columns.getKeyColumn();

        assertThat(foundColumn.isPrimaryKey()).isTrue();
    }
}
