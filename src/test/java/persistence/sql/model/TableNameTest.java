package persistence.sql.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;
import persistence.sql.exception.ExceptionMessage;
import persistence.sql.exception.RequiredClassException;
import persistence.sql.model.TableName;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("테이블 이름 Test")
class TableNameTest {

    @Test
    void Entity_이용하여_테이블_이름_가져오기() {
        TableName tableName = new TableName(Person.class);
        assertThat(tableName.getValue()).isEqualTo("users");
    }

    @Test
    void 테이블_이름_생성시_Class가_Null_일경우() {
        assertThatThrownBy(() -> new TableName(null))
                .isInstanceOf(RequiredClassException.class)
                .hasMessage(ExceptionMessage.REQUIRED_CLASS.getMessage());
    }

}
