package hibernate.entity.column;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EntityFieldTest {

    @Test
    void 생성_시_Transient_어노테이션이_있는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new EntityField(TestEntity.class.getDeclaredField("transientField")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Transient이 붙은 필드는 생성될 수 없습니다.");
    }

    @Test
    void Column_어노테이션의_name이_있는_경우_fieldName이_된다() throws NoSuchFieldException {
        String actual = new EntityField(TestEntity.class.getDeclaredField("name")).getFieldName();
        assertThat(actual).isEqualTo("nick_name");
    }

    @Test
    void Column_어노테이션의_name이_없는_경우_클래스명이_fieldName이_된다() throws NoSuchFieldException {
        String actual = new EntityField(TestEntity.class.getDeclaredField("noColumnName")).getFieldName();
        assertThat(actual).isEqualTo("noColumnName");
    }

    @Test
    void Field의_ColumnType을_저장한다() throws NoSuchFieldException {
        ColumnType actual = new EntityField(TestEntity.class.getDeclaredField("name")).getColumnType();
        assertThat(actual).isEqualTo(ColumnType.VAR_CHAR);
    }

    @Test
    void Column_어노테이션의_nullable이_있는_경우_isNullable이_된다() throws NoSuchFieldException {
        boolean actual = new EntityField(TestEntity.class.getDeclaredField("email")).isNullable();
        assertThat(actual).isFalse();
    }

    @Test
    void Column_어노테이션의_nullable이_없는_경우_isNullable은_default값이_된다() throws NoSuchFieldException {
        boolean actual = new EntityField(TestEntity.class.getDeclaredField("noNullableEmail")).isNullable();
        assertThat(actual).isTrue();
    }

    @Test
    void isId는_false이다() throws NoSuchFieldException {
        boolean actual = new EntityField(TestEntity.class.getDeclaredField("name")).isId();
        assertThat(actual).isFalse();
    }

    @Test
    void GenerationType을_반환하려하면_예외가_발생한다() {
        assertThatThrownBy(() -> new EntityField(TestEntity.class.getDeclaredField("name")).getGenerationType())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("일반 Field는 GenerationType을 호출할 수 없습니다.");
    }

    class TestEntity {
        @Column(name = "nick_name")
        private String name;

        private String noColumnName;

        @Column(nullable = false)
        private String email;

        private String noNullableEmail;

        @Transient
        private String transientField;
    }
}
