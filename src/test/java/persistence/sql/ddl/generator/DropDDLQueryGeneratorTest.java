package persistence.sql.ddl.generator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.generator.example.PersonV3;
import persistence.sql.ddl.dialect.H2ColumnType;

class DropDDLQueryGeneratorTest {

    private final DropDDLQueryGenerator dropDDLQueryGenerator = new DropDDLQueryGenerator(new H2ColumnType());

    @Test
    @DisplayName("요구사항 4에 대한 엔티티에 대한 DDL을 생성할 수 있다.")
    public void dropDDlFromEntity() {
        final String ddl = dropDDLQueryGenerator.drop(PersonV3.class);
        assertThat(ddl).isEqualTo("DROP TABLE USERS;");
    }
}