package persistence.sql.ddl;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import jakarta.persistence.Column;
import java.sql.JDBCType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("엔티티 클래스 컬럼 테스트")
class EntityColumnTest {

    @Test
    @DisplayName("필드 파라미터에 null이 들어가면 예외가 발생한다.")
    void nullField() {
        assertThatIllegalArgumentException()
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
    @DisplayName("자바 타입이 정상적으로 변환 된다.")
    void getJdbcType() throws Exception {
        class TestEntity {
            private Long id;
            private String name;
            private Integer age;
        }

        final EntityColumn idColumn = new EntityColumn(TestEntity.class.getDeclaredField("id"));
        final EntityColumn nameColumn = new EntityColumn(TestEntity.class.getDeclaredField("name"));
        final EntityColumn ageColumn = new EntityColumn(TestEntity.class.getDeclaredField("age"));

        assertThat(idColumn.getJdbcType()).isEqualTo(JDBCType.BIGINT);
        assertThat(nameColumn.getJdbcType()).isEqualTo(JDBCType.VARCHAR);
        assertThat(ageColumn.getJdbcType()).isEqualTo(JDBCType.INTEGER);
    }

}
