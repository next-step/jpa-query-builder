package persistence.sql.ddl.generator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.generator.fixture.PersonV3;
import persistence.sql.dialect.H2ColumnType;

@DisplayName("DROP DDL 생성 테스트")
class DropDDLQueryGeneratorTest {

    private final DropDDLQueryGenerator dropDDLQueryGenerator = new DropDDLQueryGenerator(new H2ColumnType());

    @Test
    @DisplayName("엔티티에 대한 DROP DDL을 생성할 수 있다.")
    public void dropDDlFromEntity() {
        final String ddl = dropDDLQueryGenerator.drop(PersonV3.class);
        assertThat(ddl).isEqualTo("DROP TABLE USERS;");
    }
}