package persistence.sql.ddl.query.builder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person1;
import persistence.entity.Person2;
import persistence.entity.Person3;
import persistence.sql.ddl.dialect.h2.H2TypeMapper;
import persistence.sql.ddl.query.EntityMappingTable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DropQueryBuilderTest {

    private EntityMappingTable entityMappingTable;
    private EntityMappingTable existTableEntityMapping;

    @BeforeEach
    void setUp() {
        this.entityMappingTable = EntityMappingTable.from(Person1.class);
        this.existTableEntityMapping = EntityMappingTable.from(Person3.class);
    }

    @DisplayName("@Table 없는 테이블을 삭제하는 쿼리를 반환한다.")
    @Test
    void dropQuery() {
        DropQueryBuilder dropQueryBuilder = new DropQueryBuilder(entityMappingTable);

        final String expected = "DROP TABLE IF EXISTS Person1;";

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