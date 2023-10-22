package persistence.sql.dml.where;

import entityloaderfixture.depth.DepthPersonFixtureEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class EntityCertificationTest {

    @DisplayName("entity class 정보를 받아 EntityCertification을 생성하여 WhereQuery를 생성한다")
    @Test
    void createEntityCertification() {
        // given
        EntityCertification<DepthPersonFixtureEntity> certification = EntityCertification.certification(DepthPersonFixtureEntity.class);

        // when
        WhereQuery whereQuery = certification.equal("id", 1L);

        // then
        assertAll(
                () -> assertThat(whereQuery.getEntityType()).isEqualTo(DepthPersonFixtureEntity.class),
                () -> assertThat(whereQuery.getTargetColumn()).isEqualTo("id"),
                () -> assertThat(whereQuery.getWhereClauseType()).isEqualTo(WhereClauseType.EQUALS),
                () -> assertThat(whereQuery.getTargetValue()).isEqualTo(1L)
        );

    }
}
