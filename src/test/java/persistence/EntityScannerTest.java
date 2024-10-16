package persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

public class EntityScannerTest {
    private static final Logger logger = LoggerFactory.getLogger(EntityScannerTest.class);

    @Test
    @DisplayName("엔티티 스캐너 스캔 테스트")
    void entityScanningTest() throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        EntityScanner entityScanner = new EntityScanner();
        entityScanner.scan("example.entity");

        Class<EntityScanner> entityScannerClass = EntityScanner.class;
        Field entityClassesField = entityScannerClass.getDeclaredField("entityClasses");
        entityClassesField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Set<Class<?>> entityClasses = (Set<Class<?>>) entityClassesField.get(entityScanner);

        logger.debug("scanned entity classed : {}", entityClasses);
    }

    @Test
    @DisplayName("엔티티 스캔 > DDL CREATE 쿼리 생성")
    void createDdlCreateQueriesTest() throws ClassNotFoundException {
        EntityScanner entityScanner = new EntityScanner();
        entityScanner.scan("example.entity");
        List<String> ddlCreateQueries = entityScanner.getDdlCreateQueries();
        logger.debug("DDL CREATE QUERIES :\n{}", ddlCreateQueries);
    }

    @Test
    @DisplayName("엔티티 스캔 > DDL DROP 쿼리 생성")
    void createDdlDropQueriesTest() throws ClassNotFoundException {
        EntityScanner entityScanner = new EntityScanner();
        entityScanner.scan("example.entity");
        List<String> ddlDropQueries = entityScanner.getDdlDropQueries();
        logger.debug("DDL DROP QUERIES :\n{}", ddlDropQueries);
    }
}
