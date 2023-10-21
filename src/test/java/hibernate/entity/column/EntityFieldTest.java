package hibernate.entity.column;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EntityFieldTest {

    @Test
    void 생성_시_Transient_어노테이션이_있는_경우_예외가_발생한다() throws NoSuchFieldException {
        Field givenField = TestEntity.class.getDeclaredField("transientField");

        assertThatThrownBy(() -> new EntityField(givenField))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Transient이 붙은 필드는 생성될 수 없습니다.");
    }

    @Test
    void Column_어노테이션의_name이_있는_경우_fieldName이_된다() throws NoSuchFieldException {
        Field givenField = TestEntity.class.getDeclaredField("name");
        String actual = new EntityField(givenField).getFieldName();
        assertThat(actual).isEqualTo("nick_name");
    }

    @Test
    void Column_어노테이션의_name이_없는_경우_클래스명이_fieldName이_된다() throws NoSuchFieldException {
        Field givenField = TestEntity.class.getDeclaredField("noColumnName");
        String actual = new EntityField(givenField).getFieldName();
        assertThat(actual).isEqualTo("noColumnName");
    }

    @Test
    void Field의_ColumnType을_저장한다() throws NoSuchFieldException {
        Field givenField = TestEntity.class.getDeclaredField("name");
        ColumnType actual = new EntityField(givenField).getColumnType();
        assertThat(actual).isEqualTo(ColumnType.VAR_CHAR);
    }

    @Test
    void Column_어노테이션의_nullable이_있는_경우_isNullable이_된다() throws NoSuchFieldException {
        Field givenField = TestEntity.class.getDeclaredField("email");
        boolean actual = new EntityField(givenField).isNullable();
        assertThat(actual).isFalse();
    }

    @Test
    void Column_어노테이션의_nullable이_없는_경우_isNullable은_default값이_된다() throws NoSuchFieldException {
        Field givenField = TestEntity.class.getDeclaredField("noNullableEmail");
        boolean actual = new EntityField(givenField).isNullable();
        assertThat(actual).isTrue();
    }

    @Test
    void isId는_false이다() throws NoSuchFieldException {
        Field givenField = TestEntity.class.getDeclaredField("name");
        boolean actual = new EntityField(givenField).isId();
        assertThat(actual).isFalse();
    }

    @Test
    void GenerationType을_반환하려하면_예외가_발생한다() throws NoSuchFieldException {
        Field givenField = TestEntity.class.getDeclaredField("name");
        assertThatThrownBy(() -> new EntityField(givenField).getGenerationType())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("일반 Field는 GenerationType을 호출할 수 없습니다.");
    }

    @Test
    void Entity객체의_필드값을_반환한다() throws NoSuchFieldException {
        TestEntity givenEntity = new TestEntity("최진영");
        Object actual = new EntityField(TestEntity.class.getDeclaredField("name"))
                .getFieldValue(givenEntity);
        assertThat(actual).isEqualTo("최진영");
    }

    @Test
    void Entity객체의_없는_필드값인_경우_예외가_발생한다() throws NoSuchFieldException {
        TestEntity2 givenEntity = new TestEntity2(1L);
        EntityField entityField = new EntityField(TestEntity.class.getDeclaredField("name"));

        assertThatThrownBy(() -> entityField.getFieldValue(givenEntity))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Entity 객체에 필드값이 없습니다.");
    }

    static class TestEntity {

        private Long id;
        @Column(name = "nick_name")
        private String name;

        private String noColumnName;

        @Column(nullable = false)
        private String email;

        private String noNullableEmail;

        @Transient
        private String transientField;

        public TestEntity(String name) {
            this.name = name;
        }
    }

    static class TestEntity2 {
        private Long non;

        public TestEntity2(Long non) {
            this.non = non;
        }
    }
}
