package persistence.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class EntityFinderTest {

    @Test
    @DisplayName("모든 엔티티를 가져오는지 테스트")
    void findAllEntity() {
        try {
            EntityFinder entityFinder = EntityFinder.class.getConstructor().newInstance();
            entityFinder.findEntity();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }



}
