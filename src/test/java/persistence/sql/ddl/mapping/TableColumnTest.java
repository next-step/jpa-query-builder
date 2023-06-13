package persistence.sql.ddl.mapping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class TableColumnTest {

    private TableColumn id;
    private TableColumn name;
    private TableColumn age;
    private TableColumn email;

    @BeforeEach
    void init() throws Exception {
        Class<Person> entityClass = Person.class;
        id = new TableColumn(entityClass.getDeclaredField("id"));
        name = new TableColumn(entityClass.getDeclaredField("name"));
        age = new TableColumn(entityClass.getDeclaredField("age"));
        email = new TableColumn(entityClass.getDeclaredField("email"));
    }

    @DisplayName("각 필드에 해당하는 컬럼 추가 구문을 생성한다.")
    @Test
    void createDdlString() {
        assertAll(() -> {
            assertThat(id.createDdlString()).isEqualTo("id bigint not null auto_increment");
            assertThat(name.createDdlString()).isEqualTo("nick_name varchar(255)");
            assertThat(age.createDdlString()).isEqualTo("old integer");
            assertThat(email.createDdlString()).isEqualTo("email varchar(255) not null");
        });
    }
}
