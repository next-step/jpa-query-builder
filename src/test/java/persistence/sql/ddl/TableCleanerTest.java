package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableCleanerTest {

    @Test
    @DisplayName("[성공] Person 테이블에 대한 drop query 검증")
    void dropQuery() {
        TableCleaner tableCleaner = new TableCleaner();
        String dropQuery = "drop table if exists users";
        assertEquals(tableCleaner.getSqlDropQueryString(Person.class), dropQuery);
    }

}
