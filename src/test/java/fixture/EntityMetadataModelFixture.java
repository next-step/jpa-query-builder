package fixture;

import entityloaderfixture.depth.DepthPersonFixtureEntity;
import persistence.core.EntityColumn;
import persistence.core.EntityMetadataModel;
import persistence.sql.dml.where.EntityCertification;
import persistence.sql.dml.where.FetchWhereQuery;
import persistence.sql.dml.where.WhereQuery;
import persistence.sql.dml.where.WhereQueryBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityMetadataModelFixture {

    public static EntityMetadataModel getEntityMetadataModel(Class<?> entity) {
        List<EntityColumn> entityColumns = Arrays.stream(entity.getDeclaredFields())
                .map(EntityColumn::new)
                .collect(Collectors.toUnmodifiableList());

        return new EntityMetadataModel(
                entity.getSimpleName(),
                entity,
                entityColumns
        );
    }
}
