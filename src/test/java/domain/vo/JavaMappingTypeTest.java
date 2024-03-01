package domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Types;

import static org.assertj.core.api.Assertions.assertThat;

class JavaMappingTypeTest {

    @DisplayName("정의한 데이터 타입에 맞는 Types 를 반환한다.")
    @Test
    void mapping_타입_테스트() {
        JavaMappingType javaMappingType = new JavaMappingType();
        assertThat(javaMappingType.getJavaTypeByClass(String.class)).isEqualTo(Types.VARCHAR);
        assertThat(javaMappingType.getJavaTypeByClass(Integer.class)).isEqualTo(Types.INTEGER);
        assertThat(javaMappingType.getJavaTypeByClass(Long.class)).isEqualTo(Types.BIGINT);
    }
}
