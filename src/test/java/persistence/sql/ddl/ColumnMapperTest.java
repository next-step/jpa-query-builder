package persistence.sql.ddl;

import jakarta.persistence.Column;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.h2.H2Dialect;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class ColumnMapperTest {
    private final ColumnMapper columnMapper = new ColumnMapper(new H2Dialect());

    @Test
    @DisplayName("@Column 의 name 설정이 있는 경우 컬럼 이름으로 사용한다")
    public void columnName() throws NoSuchFieldException {
        Field field = ColumnDummy.class.getDeclaredField("age");

        String column = columnMapper.column(field);
        assertThat(column).isEqualTo("old integer");
    }

    @Test
    @DisplayName("@Column 의 nullable 여부에 따라 조건을 생성한다")
    public void nullable() throws NoSuchFieldException {
        Field email = ColumnDummy.class.getDeclaredField("email");

        String column = columnMapper.column(email);
        assertThat(column).isEqualTo("email varchar(255) not null");
    }

    @Test
    @DisplayName("@Column 타입이 String 일 경우 length 정보를 추가한다")
    public void length() throws NoSuchFieldException {
        Field email = ColumnDummy.class.getDeclaredField("name");

        String column = columnMapper.column(email);
        assertThat(column).isEqualTo("name varchar(10)");
    }

    public static class ColumnDummy {
        @Column(length = 10)
        private String name;

        @Column(name = "old")
        private Integer age;

        @Column(nullable = false)
        private String email;
    }
}