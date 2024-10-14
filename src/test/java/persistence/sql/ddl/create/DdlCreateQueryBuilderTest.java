package persistence.sql.ddl.create;

import example.entity.Person;
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
        Class<Person> personClass = Person.class;
        for (Field field : personClass.getDeclaredFields()) {
            String columnComponent = ColumnComponentBuilder.from(field).getComponentBuilder().toString();
            logger.debug("Person.{} : {}", field.getName(), columnComponent);
        }
    }

    @Test
    @DisplayName("Constraint component 생성 테스트")
    void createConstraintComponentTest() {
        Class<Person> personClass = Person.class;
        for (Field field : personClass.getDeclaredFields()) {
            for (ConstraintComponentBuilder constraintComponentBuilder : ConstraintComponentBuilder.from(field)) {
                logger.debug("Constraint : {}", constraintComponentBuilder.getComponentBuilder().toString());
            }
        }
    }

    @Test
    @DisplayName("DDL Create query 생성 테스트")
    void createDdlCreateQueryTest() {
        Class<Person> personClass = Person.class;

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
