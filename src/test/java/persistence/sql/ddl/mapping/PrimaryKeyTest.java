package persistence.sql.ddl.mapping;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;
import persistence.sql.ddl.exception.NoIdentifierException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PrimaryKeyTest {

    @DisplayName("@Id 어노테이션이 없는 필드는 PK로 생성할 수 없다.")
    @Test
    void invalid() {
        Class<Person> entityClass = Person.class;
        assertThatThrownBy(() -> new PrimaryKey(entityClass.getDeclaredField("name")))
            .isInstanceOf(NoIdentifierException.class);
    }

    @DisplayName("primary key 구문을 생성한다.")
    @Test
    void createDdlString() throws Exception {
        Class<Person> entityClass = Person.class;
        PrimaryKey primaryKey = new PrimaryKey(entityClass.getDeclaredField("id"));
        assertThat(primaryKey.createDdlString()).isEqualTo(", primary key (id)");
    }
}
