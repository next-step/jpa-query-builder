package persistence.meta;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import java.lang.reflect.Field;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.exception.FieldEmptyException;
import persistence.exception.NumberRangeException;
import persistence.testFixtures.Person;

@DisplayName("엔티티 클래스 컬럼 테스트")
class EntityColumnTest {

    @Test
    @DisplayName("필드 파라미터에 null이 들어가면 예외가 발생한다.")
    void nullField() {
        assertThatExceptionOfType(FieldEmptyException.class)
                .isThrownBy(() -> new EntityColumn(null));
    }

    @Test
    @DisplayName("칼럼 이름이 지정되어 있으면 그 이름을 반환한다.")
    void getColumnName() throws Exception {
        //given
        class TestEntity {
            @Column(name = "test_name")
            private String name;
        }

        // when
        final EntityColumn entityColumn = new EntityColumn(TestEntity.class.getDeclaredField("name"));

        // then
        assertThat(entityColumn.getName()).isEqualTo("test_name");
    }

    @Test
    @DisplayName("칼럼 이름이 지정되어 있지 않으면 기본 파리미터 변수 명을 가져온다.")
    void getName() throws Exception {
        //given
        class TestEntity {
            private String name;
        }

        // when
        final EntityColumn entityColumn = new EntityColumn(TestEntity.class.getDeclaredField("name"));

        // then
        assertThat(entityColumn.getName()).isEqualTo("name");
    }

    @Test
    @DisplayName("필드가 pk인지 확인한다.")
    void isPk() throws Exception {
        //given
        class TestEntity {
            @Id
            private String name;
        }

        //when
        final EntityColumn entityColumn = new EntityColumn(TestEntity.class.getDeclaredField("name"));

        //then
        assertThat(entityColumn.isPk()).isTrue();
    }

    @Test
    @DisplayName("필드가 널을 허용")
    void isNull() throws Exception {
        //given
        class TestEntity {
            @Column(nullable = true)
            private String name;
        }

        //when
        final EntityColumn entityColumn = new EntityColumn(TestEntity.class.getDeclaredField("name"));

        //then
        assertThat(entityColumn.isNotNull()).isFalse();
    }

    @Test
    @DisplayName("필드가 널을 허용하지 않는다.")
    void isNotNull() throws Exception {
        class TestEntity {
            @Column(nullable = false)
            private String name;
        }

        final EntityColumn entityColumn = new EntityColumn(TestEntity.class.getDeclaredField("name"));

        assertSoftly((it) -> {
            it.assertThat(entityColumn.isVarchar()).isTrue();
            it.assertThat(entityColumn.isNotNull()).isTrue();
            it.assertThat(entityColumn.getLength()).isEqualTo(255);
        });
    }

    @Test
    @DisplayName("가변적인 길이를 가진 varchar 타입을 생성한다.")
    void availableLengthVarchar() throws Exception {
        //given
        class TestEntity {
            @Column(nullable = false, length = 2000)
            private String name;
        }

        //when
        final EntityColumn entityColumn = new EntityColumn(TestEntity.class.getDeclaredField("name"));

        //then
        assertThat(entityColumn.getLength()).isEqualTo(2000);
    }

    @Test
    @DisplayName("부정확한 길이를 가진 varchar 타입은 예외가 발생한다.")
    void isNotValidLength() throws Exception {
        //given
        class TestEntity {
            @Column(nullable = false, length = -1)
            private String name;
        }

        //when & then
        assertThatExceptionOfType(NumberRangeException.class)
                .isThrownBy(() -> new EntityColumn(TestEntity.class.getDeclaredField("name")));
    }

    @Test
    @DisplayName("객체의 필드 값을 가져온다.")
    void getFiledValue() throws Exception {
        Person person = new Person("name", 3, "kbh@gm.com");

        Field name =  Person.class.getDeclaredField("name");
        Field age =  Person.class.getDeclaredField("age");
        Field email =  Person.class.getDeclaredField("email");

        assertSoftly((it) -> {
            it.assertThat(new EntityColumn(name).getFieldValue(person)).isEqualTo("name");
            it.assertThat(new EntityColumn(age).getFieldValue(person)).isEqualTo(3);
            it.assertThat(new EntityColumn(email).getFieldValue(person)).isEqualTo("kbh@gm.com");
        });
    }
}
