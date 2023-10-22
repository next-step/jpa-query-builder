package fixture;

import entityloaderfixture.depth.DepthPersonFixtureEntity;
import persistence.sql.dml.where.EntityCertification;
import persistence.sql.dml.where.FetchWhereQuery;
import persistence.sql.dml.where.WhereQuery;
import persistence.sql.dml.where.WhereQueryBuilder;

import java.util.List;

public class FetchWhereQueryFixture {

    public static FetchWhereQuery getFixtureDepthPersonFetchWhereQuery() {
        EntityCertification<DepthPersonFixtureEntity> certification = EntityCertification.certification(DepthPersonFixtureEntity.class);
        WhereQuery idEqual = certification.equal("id", 1L);
        WhereQuery nameEqual = certification.equal("name", "ok");
        return WhereQueryBuilder.builder().and(List.of(idEqual, nameEqual));
    }
}
