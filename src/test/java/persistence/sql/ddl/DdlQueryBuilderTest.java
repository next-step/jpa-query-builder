package persistence.sql.ddl;

import jakarta.persistence.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.Person;

import static org.assertj.core.api.Assertions.*;

class DdlQueryBuilderTest {
    @Test
    @DisplayName("@Entity 애노테이션이 존재하는 클래스로 인스턴스를 생성한다.")
    void constructor() {
        // when
        final DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder(Person.class);

        // then
        assertThat(ddlQueryBuilder).isNotNull();
    }

    @Test
    @DisplayName("@Entity 애노테이션이 존재하지 않는 클래스로 인스턴스를 생성하면 예외가 발생한다.")
    void constructor_exception() {
        // when & then
        assertThatThrownBy(() -> new DdlQueryBuilder(NotEntity.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(DdlQueryBuilder.CREATE_FAILED_MESSAGE);
    }

    @Test
    @DisplayName("CREATE 쿼리를 생성한다.")
    void getCreateQuery() {
        // given
        final DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder(Person.class);

        // when
        final String query = ddlQueryBuilder.getCreateQuery();

        // then
        assertThat(query).isEqualTo(
                "CREATE TABLE users ( id BIGINT AUTO_INCREMENT PRIMARY KEY, nick_name VARCHAR(255), old INTEGER, email VARCHAR(255) NOT NULL )");
    }

    @Test
    @DisplayName("DROP 쿼리를 생성한다.")
    void getDropQuery() {
        // given
        final DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder(Person.class);

        // when
        final String query = ddlQueryBuilder.getDropQuery();

        // then
        assertThat(query).isEqualTo(
                "DROP TABLE IF EXISTS users");
    }

    static public class NotEntity {
        @Id
        private Long id;

        private String name;

        private Integer age;
    }
}
