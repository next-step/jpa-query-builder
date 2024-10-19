package persistence.entity.impl;

import persistence.entity.EntityManager;

import java.util.HashMap;
import java.util.Map;

public class FakeEntityManger implements EntityManager {
    private final Map<Class<?>, Map<Long, Object>> FakePersistContext = new HashMap<>();
    private Long fakeId = 0L;

    @Override
    public <T> T find(Class<T> clazz, Long Id) {
        return null;
    }

    @Override
    public Object persist(Object entity) {
        return null;
    }

    @Override
    public <T> T remove(Class<T> clazz, Long Id) {
        return null;
    }

    @Override
    public <T> T update(Class<T> clazz, Long Id) {
        return null;
    }
}
