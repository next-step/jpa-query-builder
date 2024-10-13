package persistence.sql.ddl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueryBuilderDDLTest {

    private class TestClass { }
    @Test
    @DisplayName("@Entity로 정의되지 않은 class 입력했을 때 - 오류 출력")
    void buildCreateDdl_error_notEntityValue() {
        QueryBuilderDDL queryBuilderDDL = QueryBuilderDDL.getInstance();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            queryBuilderDDL.buildCreateDdl(TestClass.class);
        });
    }

}
