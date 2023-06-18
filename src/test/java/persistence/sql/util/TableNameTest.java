package persistence.sql.util;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TableNameTest {
    @Test
    @DisplayName("Table 어노테이션의 name 을 읽어서 테이블 이름에 반영할 수 있다.")
    void tableAnnotation() {
        final String actual = TableName.build(
                Person.class
        );
        assertThat(actual).isEqualTo("users");
    }

    @Test
    @DisplayName("Table 어노테이션이 없다면 Class 의 이름을 테이블 이름으로 한다.")
    void className() {
        final String actual = TableName.build(
                Object.class
        );
        assertThat(actual).isEqualTo("object");
    }
}
