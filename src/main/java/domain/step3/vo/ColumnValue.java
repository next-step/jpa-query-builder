package domain.step3.vo;

import java.util.Objects;

public class ColumnValue {

    private final Object value;

    /**
     * 그 필드에 타입이 맞아야 생성이 된다.
     */
    public ColumnValue(JavaMappingType javaMappingType, Integer javaTypeByClass, Object value) {
        if (Objects.nonNull(javaTypeByClass)) {
            javaMappingType.checkJavaType(javaTypeByClass);
        }
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}
