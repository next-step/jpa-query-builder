package persistence.sql.dml.query.builder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.entity.EntityMappingTable;
import persistence.sql.entity.conditional.Criteria;
import persistence.sql.entity.conditional.Criterias;
import persistence.sql.entity.model.DomainType;
import persistence.sql.entity.model.Operators;

import java.util.Collections;

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
        Criteria criteria = new Criteria(pkDomainTypes.getColumnName(), "1", Operators.EQUALS);
        Criterias criterias = new Criterias(Collections.singletonList(criteria));

        DeleteQueryBuilder deleteQueryBuilder = DeleteQueryBuilder.of("person", criterias);

        assertThat(deleteQueryBuilder.toSql()).isEqualTo("DELETE FROM person where id='1'");
    }

}
