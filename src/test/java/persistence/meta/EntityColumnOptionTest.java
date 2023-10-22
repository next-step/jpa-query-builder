package persistence.meta;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.lang.reflect.Field;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.exception.FieldEmptyException;

@DisplayName("컬럼에 대한 옵션 테스트")
class EntityColumnOptionTest {

    @Test
    @DisplayName("엔티티 칼럼 메타 데이터 클래스의 인수가 없으면 예외가 발생한다.")
    void noArgs() {
        assertThatExceptionOfType(FieldEmptyException.class)
                .isThrownBy(() -> new EntityColumnOption(null));
    }

    @Test
    @DisplayName("id 어노테이션이 붙은 필드는 pk이다.")
    void noEntity() throws Exception{
        //given
        class TestEntity {
            @Id
            private Long id;
        }

        //when
        Field field = TestEntity.class.getDeclaredField("id");
        EntityColumnOption option = new EntityColumnOption(field);


        //then
        assertThat(option.isPk()).isTrue();
    }

    @Test
    @DisplayName("@GeneratedValue(strategy = GenerationType.IDENTITY)어노테이션이 붙어 있으면 생성 전락이 IDENTITY 이다.")
    void generationType() throws Exception{
        //given
        class TestEntity {
            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private Long id;
        }

        //when
        Field field = TestEntity.class.getDeclaredField("id");
        EntityColumnOption option = new EntityColumnOption(field);

        //then
        assertSoftly(it -> {
            it.assertThat(option.isPk()).isTrue();
            it.assertThat(option.hasGenerationValue()).isTrue();
            it.assertThat(option.getGenerationType()).isEqualTo(GenerationType.IDENTITY);
        });
    }

    @Test
    @DisplayName("id 어노테이션이 안 붙어있으면 GenerationType이 설정되지 않는다.")
    void noGenerationType() throws Exception{
        //given
        class TestEntity {
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private Long id;
        }

        //when
        Field field = TestEntity.class.getDeclaredField("id");
        EntityColumnOption option = new EntityColumnOption(field);

        //then
        assertSoftly(it -> {
            it.assertThat(option.isPk()).isFalse();
            it.assertThat(option.hasGenerationValue()).isFalse();
        });
    }

    @Test
    @DisplayName("@Column 어노테이션에 nullable이 false이면 널을 방지한다.")
    void notNull() throws Exception{
        //given
        class TestEntity {

            @Column(nullable = false)
            private Long id;
        }

        //when
        Field field = TestEntity.class.getDeclaredField("id");
        EntityColumnOption option = new EntityColumnOption(field);

        //then
        assertThat(option.isNullable()).isFalse();
    }

    @Test
    @DisplayName("@Column 어노테이션에 nullable 설정되어 있지 않으면 null을 허용한다.")
    void nullable() throws Exception{
        //given
        class TestEntity {
            private Long id;
        }

        //when
        Field field = TestEntity.class.getDeclaredField("id");
        EntityColumnOption option = new EntityColumnOption(field);

        //then
        assertThat(option.isNullable()).isTrue();
    }

}
