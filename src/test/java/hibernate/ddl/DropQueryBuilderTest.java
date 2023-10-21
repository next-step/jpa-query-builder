package hibernate.ddl;

import hibernate.entity.EntityClass;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class DropQueryBuilderTest {

    private final DropQueryBuilder dropQueryBuilder = new DropQueryBuilder();

    @Test
    void drop쿼리를_생성한다() {
        Pattern expected = Pattern.compile("drop table testentity");
        String actual = dropQueryBuilder.generateQuery(new EntityClass(TestEntity.class))
                .toLowerCase();
        assertThat(actual).matches(expected);
    }

    @Test
    void Table어노테이션이_있는_경우_해당_이름으로_drop쿼리를_생성한다() {
        Pattern expected = Pattern.compile("drop table table_option");
        String actual = dropQueryBuilder.generateQuery(new EntityClass(TestEntity2.class))
                .toLowerCase();
        assertThat(actual).matches(expected);
    }

    @Entity
    static class TestEntity {
        @Id
        private Long id;
    }

    @Entity
    @Table(name = "table_option")
    static class TestEntity2 {
        @Id
        private Long id;
    }
}
