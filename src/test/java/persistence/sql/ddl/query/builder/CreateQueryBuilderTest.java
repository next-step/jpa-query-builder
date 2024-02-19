package persistence.sql.ddl.query.builder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person1;
import persistence.sql.ddl.dialect.h2.H2TypeMapper;
import persistence.sql.ddl.query.EntityMappingTable;

import static org.assertj.core.api.Assertions.assertThat;

class CreateQueryBuilderTest {

    private EntityMappingTable entityMappingTable;

    @BeforeEach
    void setUp() {
        this.entityMappingTable = EntityMappingTable.from(Person1.class);
    }

    @DisplayName("테이블 만드는 쿼리문을 반환한다.")
    @Test
    void createQuery() {
        CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(entityMappingTable);

        final String expected = "CREATE TABLE Person1(\n" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
                "name VARCHAR,\n" +
                "age INTEGER\n" +
                ");";

        assertThat(createQueryBuilder.toSql(H2TypeMapper.newInstance())).isEqualTo(expected);
    }

}