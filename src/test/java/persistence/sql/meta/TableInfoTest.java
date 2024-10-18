package persistence.sql.meta;

import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class TableInfoTest {

    @Entity
    public class Dummy {

    }

    @Table(name = "users")
    @Entity
    public class Dummy2 {

    }


    @Test
    @DisplayName("임의의 class에서 테이블 Entity 이름 가져오기")
    void getTableEntityName() {
        assertThat(new TableInfo(Dummy.class).getTableName()).isEqualTo("dummy");
    }

    @Test
    @DisplayName("임의의 class에서 테이블 이름 가져오기")
    void getTableName() {
        assertThat(new TableInfo(Dummy2.class).getTableName()).isEqualTo("users");
    }

}
