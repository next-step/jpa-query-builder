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
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class SelectQueryBuilderTest {

    private EntityMappingTable entityMappingTable;

    @BeforeEach
    void setUp() {
        this.entityMappingTable = EntityMappingTable.from(Person.class);
    }

    @DisplayName("조건문 없는 SELECT문을 반환한다.")
    @Test
    void sqlTest() {
        SelectQueryBuilder selectQueryBuilder = SelectQueryBuilder.from(entityMappingTable);

        assertThat(selectQueryBuilder.toSql()).isEqualTo("SELECT id,nick_name,old,email FROM Person ");
    }

    @DisplayName("조건문 있는 SELECT문을 반환한다.")
    @Test
    void whereSqlTest() {
        DomainType domainType = entityMappingTable.getPkDomainTypes();
        Map<DomainType, String> where = Map.of(domainType, "1");

        Criteria criteria = new Criteria(domainType.getColumnName(), "1", Operators.EQUALS);
        Criterias criterias = new Criterias(Collections.singletonList(criteria));

        SelectQueryBuilder selectQueryBuilder = SelectQueryBuilder.of(entityMappingTable, criterias);

        assertThat(selectQueryBuilder.toSql()).isEqualTo("SELECT id,nick_name,old,email FROM Person where id='1'");
    }

}
