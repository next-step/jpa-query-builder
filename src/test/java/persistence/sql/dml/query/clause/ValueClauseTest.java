package persistence.sql.dml.query.clause;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.entity.User;
import persistence.sql.dml.exception.IllegalFieldValueException;
import persistence.sql.dml.exception.NotFoundFieldNameException;
import persistence.sql.entity.EntityMappingTable;
import persistence.sql.entity.model.DomainTypes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ValueClauseTest {

    private DomainTypes domainTypes;
    private Person person;

    @BeforeEach
    void setUp() {
        this.domainTypes = EntityMappingTable.from(Person.class).getDomainTypes();
        this.person = new Person(1L, "신성수", 20, "tlstjdtn@nextstep.com");
    }

    @DisplayName("쿼리문에 쓰일 값들을 반환한다.")
    @Test
    void valueSql() {
        ValueClause valueClause = ValueClause.from(person, domainTypes);

        assertThat(valueClause.toSql()).isEqualTo("1,'신성수',20,'tlstjdtn@nextstep.com'");
    }

    @DisplayName("필드이름을 찾지 못할떄, 에러를 발생시킨다.")
    @Test
    void notFoundFieldNameException() {
        DomainTypes userDomainTypes = EntityMappingTable.from(User.class).getDomainTypes();

        assertThatThrownBy(() ->ValueClause.from(person, userDomainTypes))
                .isInstanceOf(NotFoundFieldNameException.class)
                .hasMessage("필드이름을 찾지 못했습니다.");
    }

    @DisplayName("값에 널값이 있으면 에러를 발생시킨다.")
    @Test
    void IllegalFieldValueException() {
        Person existsNullperson = new Person();

        assertThatThrownBy(() ->ValueClause.from(existsNullperson, domainTypes))
                .isInstanceOf(IllegalFieldValueException.class)
                .hasMessage("적절하지 않은 필드 값 입니다.");
    }

}