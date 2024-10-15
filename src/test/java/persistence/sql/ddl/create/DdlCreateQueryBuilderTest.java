package persistence.sql.ddl.create;

import example.entity.PersonV1;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.create.component.column.ColumnComponentBuilder;
import persistence.sql.ddl.create.component.constraint.ConstraintComponentBuilder;

import java.lang.reflect.Field;

public class DdlCreateQueryBuilderTest {
    private static final Logger logger = LoggerFactory.getLogger(DdlCreateQueryBuilderTest.class);

    @Test
    @DisplayName("Column component 생성 테스트")
    void createColumnComponentTest() {
        Class<PersonV1> personClass = PersonV1.class;
        for (Field field : personClass.getDeclaredFields()) {
            String columnComponent = ColumnComponentBuilder.from(field).build();
            logger.debug("Person.{} : {}", field.getName(), columnComponent);
        }
    }

    @Test
    @DisplayName("Constraint component 생성 테스트")
    void createConstraintComponentTest() {
        Class<PersonV1> personClass = PersonV1.class;
        for (Field field : personClass.getDeclaredFields()) {
            for (ConstraintComponentBuilder constraintComponentBuilder : ConstraintComponentBuilder.from(field)) {
                logger.debug("Constraint : {}", constraintComponentBuilder.build().toString());
            }
        }
    }

    @Test
    @DisplayName("DDL Create query 생성 테스트")
    void createDdlCreateQueryTest() {
        Class<PersonV1> personClass = PersonV1.class;

        Field[] fields = personClass.getDeclaredFields();
        DdlCreateQueryBuilder queryBuilder = DdlCreateQueryBuilder.newInstance();
        for (Field field : fields) {
            queryBuilder.add(ColumnComponentBuilder.from(field));
            queryBuilder.add(ConstraintComponentBuilder.from(field));
        }
        String ddlQuery = queryBuilder.build(personClass.getSimpleName());

        logger.debug("DDL Query : {}", ddlQuery);
    }
}