package persistence.sql.ddl;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.view.mysql.MySQLPrimaryKeyResolver;
import persistence.sql.entity.Person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DdlQueryBuilderTest {

    private final DdlQueryBuilder builder = new DdlQueryBuilder(new MySQLPrimaryKeyResolver());

    @Test
    void should_create_create_query() {
        assertThat(builder.createQuery(Person.class)).isEqualTo("CREATE TABLE users(" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                "nick_name VARCHAR(255)," +
                "old INT," +
                "email VARCHAR(255) NOT NULL" +
                ");");
    }

    @Test
    void should_create_drop_query() {
        assertThat(builder.dropQuery(Person.class)).isEqualTo("DROP TABLE users;");
    }

    @Test
    void should_throw_exception_when_class_is_not_entity() {
        assertThatThrownBy(() -> builder.createQuery(NoEntityAnnotationClass.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("entity annotation is required");
    }

    class NoEntityAnnotationClass {
    }
}
