package persistence.sql.metadata;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.exception.NotExistException;
import persistence.sql.ddl.fixture.PersonWithEntityIdFixture;
import persistence.sql.ddl.fixture.PersonWithEntityNamePropertyFixture;
import persistence.sql.ddl.fixture.PersonWithoutEntityFixture;

class TableNameTest {

    @Test
    @DisplayName("[실패] @Entity 애노테이션이 없는 경우 NotExistException 발생")
    void notExistEntityAnnotation() {
        assertThatThrownBy(() -> new TableName(PersonWithoutEntityFixture.class))
                .isInstanceOf(NotExistException.class)
                .hasMessage("Not exist @Entity annotation. class = persistence.sql.ddl.fixture.PersonWithoutEntityFixture");
    }

    @Test
    @DisplayName("[성공] @Table 애노테이션이 있는 경우 Table.name 속성으로 테이블명 초기화")
    void tableName() {
        assertEquals(new TableName(Person.class).value(), "users");
    }

    @Test
    @DisplayName("[성공] @Entity 애노테이션에 name 속성이 정의된 경우 Entity.name 속성으로 테이블명 초기화")
    void entityNameProperties() {
        assertEquals(new TableName(PersonWithEntityNamePropertyFixture.class).value(), "users");
    }

    @Test
    @DisplayName("[성공] @Entity 애노테이션만 있는 경우 클래스명으로 테이블명 초기화")
    void onlyEntity() {
        assertEquals(new TableName(PersonWithEntityIdFixture.class).value(), "PersonWithEntityIdFixture");
    }

}
