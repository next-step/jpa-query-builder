package persistence.sql.dml.query.builder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.entity.EntityMappingTable;
import persistence.sql.entity.model.DomainType;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class DeleteQueryBuilderTest {

    private EntityMappingTable entityMappingTable;

    @BeforeEach
    void setUp() {
        this.entityMappingTable = EntityMappingTable.from(Person.class);
    }

    @DisplayName("삭제 쿼리문을 반환한다.")
    @Test
    void deleteById() {
        DomainType pkDomainTypes = entityMappingTable.getPkDomainTypes();
        Map<DomainType, String> where =  Map.of(pkDomainTypes, "1");
        DeleteQueryBuilder deleteQueryBuilder = DeleteQueryBuilder.of("person", where);

        assertThat(deleteQueryBuilder.toSql()).isEqualTo("DELETE FROM person where id='1'");
    }

}