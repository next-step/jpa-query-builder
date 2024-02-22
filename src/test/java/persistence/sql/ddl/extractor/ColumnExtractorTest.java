package persistence.sql.ddl.extractor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.dialect.H2Dialect;

import static org.junit.jupiter.api.Assertions.*;

@Entity
class TestClass {
    @Id
    private int id;

    @Transient
    private String ignore;

}

class ColumnExtractorTest {
    Dialect dialect = new H2Dialect();

    @Test
    @DisplayName("Transient 필드로 생성자 직접호출시 에러")
    void errorWhenHasTransientField() {
        assertThrows(ColumExtractorCreateException.class, () -> {
            new ColumnExtractor(dialect, TestClass.class.getDeclaredField("ignore"));
        });
    }
}
