package domain;

import domain.step2.H2DataType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class H2DataTypeTest {

    @DisplayName("정의한 데이터 타입에 맞는 H2DataType 을 반환한다.")
    @Test
    void 데이터_타입_exception_테스트() {
        assertAll(
                () -> assertThat(H2DataType.from(Integer.class)).isEqualTo(H2DataType.INT),
                () -> assertThat(H2DataType.from(Long.class)).isEqualTo(H2DataType.BIGINT),
                () -> assertThat(H2DataType.from(String.class)).isEqualTo(H2DataType.VARCHAR)
        );
    }
}
