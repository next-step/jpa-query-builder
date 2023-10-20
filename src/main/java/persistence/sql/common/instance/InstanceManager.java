package persistence.sql.common.instance;

import persistence.sql.common.meta.EntityManager;

import java.util.Arrays;

public class InstanceManager {
    private EntityManager entityManager;
    private Value[] values;

    public <T> InstanceManager(EntityManager entityManager, T t) {
        this.entityManager = entityManager;
        this.values = Value.of(t);;
    }

    /**
     * 값을 ','으로 이어 한 문자열로 반환합니다.
     * 예) "홍길동, 13, F"
     */
    public String getValuesWithComma() {
        return entityManager.withComma(Arrays.stream(values)
                .map(Value::getValue)
                .toArray(String[]::new));
    }
}
