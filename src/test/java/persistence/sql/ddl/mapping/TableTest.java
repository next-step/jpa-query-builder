package persistence.sql.ddl.mapping;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;
import persistence.sql.ddl.fixture.PersonWithEntityIdFixture;
import persistence.sql.ddl.fixture.PersonWithEntityNamePropertyFixture;
import persistence.sql.ddl.fixture.PersonWithoutEntityFixture;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class TableTest {

    @Test
    @DisplayName("[실패] @Entity 애노테이션이 없는 경우 RuntimeException 발생")
    void notExistEntityAnnotation() {
        assertThatThrownBy(() -> Table.from(PersonWithoutEntityFixture.class))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("@Entity not exist. class = persistence.sql.ddl.fixture.PersonWithoutEntityFixture");
    }

    @Test
    @DisplayName("[성공] @Table 애노테이션이 있는 경우 Table.name 속성으로 테이블명 초기화")
    void tableName() {
        assertEquals(Table.from(Person.class).name(), "users");
    }

    @Test
    @DisplayName("[성공] @Entity 애노테이션에 name 속성이 정의된 경우 Entity.name 속성으로 테이블명 초기화")
    void entityNameProperties() {
        assertEquals(Table.from(PersonWithEntityNamePropertyFixture.class).name(), "users");
    }

    @Test
    @DisplayName("[성공] @Entity 애노테이션만 있는 경우 클래스명으로 테이블명 초기화")
    void onlyEntity() {
        assertEquals(Table.from(PersonWithEntityIdFixture.class).name(), "PersonWithEntityIdFixture");
    }

}
