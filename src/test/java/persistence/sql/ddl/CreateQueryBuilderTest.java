package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.domain.Person;
import persistence.domain.PersonWithCustomColumnName;
import persistence.domain.PersonWithCustomTableNameAndTransientColumn;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CreateQueryBuilderTest {

    @DisplayName("@Entity가 붙은 클래스와 매핑되는 DDL생성 가능")
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

    @DisplayName("@Entity가 붙어있지 않은 경우, DDL 생성 불가")
    @Test
    void canNotCreateQueryBuilderFromNonEntityAnnotatedClass() {
        assertThatThrownBy(() -> CreateQueryBuilder.from(int.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("@Entity가 존재하지 않습니다");
    }

    @DisplayName("@Id 생성전략, @Column 제약사항 지키는 DDL 생성 가능")
    @Test
    void canCreateDDLUsingIdGenerationStrategyAndColumnConstraints() {

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

    @DisplayName("@Table name 속성, @Transient 제약사항 지키는 DDL 생성 가능")
    @Test
    void canCreateDDLUsingCustomTableNameAndTransientField() {

        final CreateQueryBuilder queryBuilder = CreateQueryBuilder.from(PersonWithCustomTableNameAndTransientColumn.class);

        final String expected = String.join(System.lineSeparator(),
                "CREATE TABLE users (",
                "id BIGINT PRIMARY KEY AUTO_INCREMENT,",
                "nick_name VARCHAR,",
                "old INTEGER,",
                "email VARCHAR NOT NULL",
                ");"
        );

        assertThat(queryBuilder.toSQL()).isEqualTo(expected);
    }
}
