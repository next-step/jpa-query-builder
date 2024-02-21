package persistence.sql.ddl.query.builder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.LegacyPerson;
import persistence.entity.User;
import persistence.sql.ddl.query.EntityMappingTable;

import static org.assertj.core.api.Assertions.assertThat;

class DropQueryBuilderTest {

    private EntityMappingTable entityMappingTable;
    private EntityMappingTable existTableEntityMapping;

    @BeforeEach
    void setUp() {
        this.entityMappingTable = EntityMappingTable.from(LegacyPerson.class);
        this.existTableEntityMapping = EntityMappingTable.from(User.class);
    }

    @DisplayName("@Table 없는 테이블을 삭제하는 쿼리를 반환한다.")
    @Test
    void dropQuery() {
        DropQueryBuilder dropQueryBuilder = new DropQueryBuilder(entityMappingTable);

        final String expected = "DROP TABLE IF EXISTS LegacyPerson;";

        assertThat(dropQueryBuilder.toSql()).isEqualTo(expected);
    }

    @DisplayName("@Table 있는 테이블 삭제하는 쿼리를 반환한다.")
    @Test
    void isExistDropQuery() {
        DropQueryBuilder dropQueryBuilder = new DropQueryBuilder(existTableEntityMapping);
        final String expected = "DROP TABLE IF EXISTS users;";

        assertThat(dropQueryBuilder.toSql()).isEqualTo(expected);
    }
}
