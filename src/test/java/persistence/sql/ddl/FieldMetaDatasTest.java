package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;

import static org.assertj.core.api.Assertions.assertThat;

class FieldMetaDatasTest {

    @Test
    @DisplayName("Person 엔터티 컬럼 정보 가져오기")
    public void getColumnInfoCollectionTest() {
        FieldMetaDatas fieldMetaDatas = new FieldMetaDatas(Person.class);

        assertThat(fieldMetaDatas.getDefinition())
                .isEqualTo("id BIGINT AUTO_INCREMENT PRIMARY KEY,nick_name VARCHAR(255),old INT,email VARCHAR(255) NOT NULL");
    }

}
