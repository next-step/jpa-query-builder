package persistence.sql.ddl;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.exception.FiledEmptyException;

@DisplayName("엔티티 클래스 컬럼 테스트")
class EntityColumnTest {

    @Test
    @DisplayName("필드 파라미터에 null이 들어가면 예외가 발생한다.")
    void nullField() {
        assertThatExceptionOfType(FiledEmptyException.class)
                .isThrownBy(() -> new EntityColumn(null));
    }

    @Test
    @DisplayName("칼럼 이름이 지정되어 있으면 그 이름을 반환한다.")
    void getColumnName() throws Exception {
        class TestEntity {
            @Column(name = "test_name")
            private String name;
        }

        final EntityColumn entityColumn = new EntityColumn(TestEntity.class.getDeclaredField("name"));

        assertThat(entityColumn.getName()).isEqualTo("test_name");
    }

    @Test
    @DisplayName("칼럼 이름이 지정되어 있지 않으면 기본 파리미터 변수 명을 가져온다.")
    void getName() throws Exception {
        class TestEntity {
            private String name;
        }

        final EntityColumn entityColumn = new EntityColumn(TestEntity.class.getDeclaredField("name"));

        assertThat(entityColumn.getName()).isEqualTo("name");
    }

    @Test
    @DisplayName("필드가 pk인지 확인한다.")
    void isPk() throws Exception {
        class TestEntity {
            @Id
            private String name;
        }

        final EntityColumn entityColumn = new EntityColumn(TestEntity.class.getDeclaredField("name"));

        assertSoftly((it) -> {
            it.assertThat(entityColumn.isPk()).isTrue();
        });
    }

    @Test
    @DisplayName("필드가 널을 허용하는 쿼리를 만든다.")
    void isNull() throws Exception {
        class TestEntity {
            @Column(nullable = true)
            private String name;
        }
        final EntityColumn entityColumn = new EntityColumn(TestEntity.class.getDeclaredField("name"));

        assertSoftly((it) -> {
            it.assertThat(entityColumn.createColumQuery()).isEqualTo("name varchar(255)");
        });
    }

    @Test
    @DisplayName("필드가 널을 허용하지 않은 쿼리를 만든다.")
    void isNotNull() throws Exception {
        class TestEntity {
            @Column(nullable = false)
            private String name;
        }
        final EntityColumn entityColumn = new EntityColumn(TestEntity.class.getDeclaredField("name"));

        assertSoftly((it) -> {
            it.assertThat(entityColumn.createColumQuery()).isEqualTo("name varchar(255) not null");
        });
    }
}
