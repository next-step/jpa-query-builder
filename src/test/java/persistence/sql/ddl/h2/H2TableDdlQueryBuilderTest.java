package persistence.sql.ddl.h2;

import domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.core.EntityMetadataModel;
import persistence.core.EntityMetadataModelFactory;
import persistence.core.EntityMetadataModels;
import persistence.sql.ddl.TableDdlQueryBuilder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class H2TableDdlQueryBuilderTest {

    EntityMetadataModelFactory entityMetadataModelFactory;

    TableDdlQueryBuilder tableDdlQueryBuilder;

    @BeforeEach
    void setUp() {
        entityMetadataModelFactory = new EntityMetadataModelFactory();
        tableDdlQueryBuilder = new H2TableDdlQueryBuilder(new H2Dialect());
    }

    @DisplayName("Person Entity를 읽어 create DDL Query를 생성한다")
    @Test
    void createPersonTableQuery() {
        // given
        EntityMetadataModel entityMetadataModel = getPersonMetadataModel();

        // when
        String query = tableDdlQueryBuilder.createDdlQuery(entityMetadataModel);

        // then
        assertThat(query).isEqualTo("create table users(id bigint auto_increment, nick_name varchar(255) null, email varchar(255) not null, old null, primary key (id))");
    }

    @DisplayName("Person Entity를 읽어 drop DDL Query를 생성한다")
    @Test
    void createDropPersonTableQuery() {
        // given
        EntityMetadataModel entityMetadataModel = getPersonMetadataModel();

        // when
        String dropTableQuery = tableDdlQueryBuilder.createDropTableQuery(entityMetadataModel);

        // then
        assertThat(dropTableQuery).isEqualTo("drop table if exists users");
    }

    private EntityMetadataModel getPersonMetadataModel() {
        EntityMetadataModels entityMetadataModels = entityMetadataModelFactory.createEntityMetadataModels(List.of(Person.class));

        return entityMetadataModels.getMetadataModels().stream()
                .findFirst()
                .orElseThrow();
    }
}
