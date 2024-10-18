package persistence.sql.dml.query;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static persistence.sql.dml.query.DMLQueryBuilderRegistry.getQueryBuilder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DMLQueryBuilderRegistryTest {

    @Test
    @DisplayName("[성공] DMLType 에 해당하는 DMLQueryBuilder 조회")
    void dmlQueryBuilderRegistry() {
        assertAll("getQueryBuilder(DMLType)",
                () -> assertEquals(getQueryBuilder(DMLType.SELECT).getClass(), SelectQueryBuilder.class),
                () -> assertEquals(getQueryBuilder(DMLType.INSERT).getClass(), InsertQueryBuilder.class),
                () -> assertEquals(getQueryBuilder(DMLType.DELETE).getClass(), DeleteQueryBuilder.class));
    }

}
