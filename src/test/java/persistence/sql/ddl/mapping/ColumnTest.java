package persistence.sql.ddl.mapping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ColumnTest {

    private Column id;
    private Column name;
    private Column age;

    @BeforeEach
    void init() throws Exception {
        Class<Person> entityClass = Person.class;
        id = new Column(entityClass.getDeclaredField("id"));
        name = new Column(entityClass.getDeclaredField("name"));
        age = new Column(entityClass.getDeclaredField("age"));
    }

    @DisplayName("각 필드에 해당하는 컬럼 추가 구문을 생성한다.")
    @Test
    void createDdlString() {
        assertAll(() -> {
            assertThat(id.createDdlString()).isEqualTo("id bigint not null");
            assertThat(name.createDdlString()).isEqualTo("name text");
            assertThat(age.createDdlString()).isEqualTo("age integer");
        });
    }
}