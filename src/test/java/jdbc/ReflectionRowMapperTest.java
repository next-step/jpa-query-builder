package jdbc;

import jakarta.persistence.*;
import org.h2.tools.SimpleResultSet;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Types;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReflectionRowMapperTest {

    @Test
    void ResultSet의_결과로_객체를_매핑한다() throws SQLException {
        // given
        SimpleResultSet givenResultSet = new SimpleResultSet();
        givenResultSet.addColumn("id", Types.BIGINT, 0, 0);
        givenResultSet.addColumn("nick_name", Types.VARCHAR, 0, 0);
        givenResultSet.addRow(1L, "최진영");
        RowMapper<TestEntity> rowMapper = new ReflectionRowMapper<>(TestEntity.class);

        // when
        TestEntity actual = rowMapper.mapRow(givenResultSet);

        // then
        assertAll(
                () -> assertThat(actual.id).isEqualTo(1L),
                () -> assertThat(actual.name).isEqualTo("최진영")
        );
    }

    @Entity
    @Table(name = "test_entity")
    public static class TestEntity {
        @Id
        private Long id;

        @Column(name = "nick_name")
        private String name;

        @Transient
        private String email;
    }
}
