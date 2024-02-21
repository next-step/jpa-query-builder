package persistence.sql.ddl.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TableTest {

    private final persistence.sql.ddl.model.Table table = new persistence.sql.ddl.model.Table();

    @Test
    @DisplayName("@Table 어노테이션이 존재하지 않으면 소문자의 클래스명을 생성한다.")
    void tableCreateTest_withoutTable() {
        final var expected = "CREATE TABLE fixture";

        final var actual = table.create(Fixture.class);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("@Table 어노테이션이 존재하지만, name property 가 존재하지 않으면 소문자의 클래스명을 생성한다.")
    void tableCreateTest_withoutName() {
        final var expected = "CREATE TABLE fixturewithoutname";

        final var actual = table.create(FixtureWithoutName.class);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("@Table 어노테이션과 name property 가 존재하면, name 값으로 생성한다.")
    void tableCreateTest_withName() {
        final var expected = "CREATE TABLE fixtures";

        final var actual = table.create(FixtureWithName.class);

        assertThat(actual).isEqualTo(expected);
    }


    @Entity
    static class Fixture {
        @Id
        private Long id;
    }

    @Table
    @Entity
    static class FixtureWithoutName {
        @Id
        private Long id;
    }

    @Table(name = "fixtures")
    @Entity
    static class FixtureWithName {
        @Id
        private Long id;
    }

}
