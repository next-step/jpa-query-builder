package persistence.sql.dml;

import entityloaderfixture.depth.DepthPersonFixtureEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.core.EntityColumn;
import persistence.core.EntityMetadataModel;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class H2TableDmlQueryBuilderTest {


    @DisplayName("")
    @Test
    void createInsertQuery () {
        // given
        Class<DepthPersonFixtureEntity> personFixtureEntityClass = DepthPersonFixtureEntity.class;

        List<EntityColumn> entityColumns = Arrays.stream(personFixtureEntityClass.getDeclaredFields())
                .map(EntityColumn::new)
                .collect(Collectors.toUnmodifiableList());

        EntityMetadataModel entityMetadataModel = new EntityMetadataModel(
                personFixtureEntityClass.getSimpleName(),
                entityColumns
        );

        // when
        DepthPersonFixtureEntity depthPersonFixtureEntity = new DepthPersonFixtureEntity("리리미", 30);
        TableDmlQueryBuilder tableDmlQueryBuilder = new H2TableDmlQueryBuilder();

        String insertQuery = tableDmlQueryBuilder.createInsertQuery(entityMetadataModel, depthPersonFixtureEntity);

        // then
        assertThat(insertQuery).isEqualTo("insert into DepthPersonFixtureEntity(name, age) values ('리리미', 30)");
    }

}
