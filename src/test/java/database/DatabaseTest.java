package database;

import org.junit.jupiter.api.Test;
import persistence.fixture.TestEntityFixture;
import persistence.sql.ddl.attribute.EntityAttribute;
import persistence.sql.ddl.parser.AttributeParser;
import persistence.sql.dml.builder.DMLQueryBuilder;
import persistence.sql.dml.builder.InsertDMLQueryBuilder;
import persistence.sql.dml.value.EntityValue;
import persistence.sql.dml.wrapper.DMLWrapper;
import persistence.sql.dml.wrapper.InsertDMLWrapper;
import persistence.sql.infra.H2SqlConverter;

import java.sql.SQLException;

public class DatabaseTest {

    @Test
    void test() throws SQLException {
        DatabaseServer database = new H2();
        DMLQueryBuilder dmlQueryBuilder = new InsertDMLQueryBuilder();
        AttributeParser parser = new AttributeParser(new H2SqlConverter());

        EntityAttribute entityAttribute = EntityAttribute.of(TestEntityFixture.EntityWithValidAnnotation.class, parser);

        TestEntityFixture.EntityWithValidAnnotation entity =
                new TestEntityFixture.EntityWithValidAnnotation("민준", 29);

        DMLWrapper wrapper = new InsertDMLWrapper();
        EntityValue entityValue = entityAttribute.makeEntityValue(entity);
        String insertDML = dmlQueryBuilder.prepareStatement(entityValue);
        
        database.executeQuery(insertDML);
    }
}
