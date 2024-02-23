package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.domain.Person;
import persistence.domain.PersonWithCustomColumnName;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CreateQueryBuilderTest {

    @DisplayName("@Entity가 붙은 클래스와 매핑되는 DDL을 만들수 있어야 한다.")
    @Test
    void canCreateDDLFromEntityAnnotatedClass() {

        final CreateQueryBuilder queryBuilder = CreateQueryBuilder.from(Person.class);

        final String expected = String.join(System.lineSeparator(),
                "CREATE TABLE Person (",
                "id BIGINT PRIMARY KEY,",
                "name VARCHAR,",
                "age INTEGER",
                ");"
        );

        assertThat(queryBuilder.toSQL()).isEqualTo(expected);

    }

    @DisplayName("@Entity가 붙어있지 않은 경우, 매핑되는 DDL은 존재하지 않아야 한다.")
    @Test
    void canNotCreateQueryBuilderFromNonEntityAnnotatedClass() {
        assertThatThrownBy(() -> CreateQueryBuilder.from(int.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("@Entity가 존재하지 않습니다");
    }

    @DisplayName("@Id 생성전략, @Column 제약사항을 지키는 테이블을 생성할수 있어야 한다")
    @Test
    void createTableFromEntityAnnotatedClass() {

        final CreateQueryBuilder queryBuilder = CreateQueryBuilder.from(PersonWithCustomColumnName.class);

        final String expected = String.join(System.lineSeparator(),
                "CREATE TABLE PersonWithCustomColumnName (",
                "id BIGINT PRIMARY KEY AUTO_INCREMENT,",
                "nick_name VARCHAR,",
                "old INTEGER,",
                "email VARCHAR NOT NULL",
                ");"
        );

        assertThat(queryBuilder.toSQL()).isEqualTo(expected);

    }
}
