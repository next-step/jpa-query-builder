package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.lang.reflect.Field;

import static persistence.sql.ddl.CommonConstant.EMPTY_STR;
import static persistence.sql.ddl.CommonConstant.SPACE;

public class MySQLDdlQueryBuilder extends AbstractDdlQueryBuilder {

    private static final String PRIMARY_KEY = "PRIMARY KEY";
    private static final String AUTO_INCREMENT = "AUTO_INCREMENT";
    private static final String NOT_NULL = "NOT NULL";

    public MySQLDdlQueryBuilder() {
        super(MySQLColumnType::convert);
    }

    @Override
    protected String addConstraints(Field field) {
        return addPrimaryKeyConstraint(field) +
                addNullConstraint(field);
    }


    private String addPrimaryKeyConstraint(Field field) {
        StringBuilder sb = new StringBuilder();
        if (field.isAnnotationPresent(Id.class)) {
            sb.append(SPACE).append(PRIMARY_KEY);
        }
        if (field.isAnnotationPresent(GeneratedValue.class)) {
            sb.append(SPACE).append(addIncrementStrategy(field));
        }
        return sb.toString();
    }

    private String addIncrementStrategy(Field field) {
        GenerationType strategy = field.getAnnotation(GeneratedValue.class).strategy();
        if (strategy.equals(GenerationType.IDENTITY) || strategy.equals(GenerationType.AUTO)) {
            return AUTO_INCREMENT;
        }
        throw new IllegalArgumentException();
    }

    private String addNullConstraint(Field field) {
        Column annotation = field.getAnnotation(Column.class);
        if (annotation != null && !annotation.nullable()) {
            return SPACE + NOT_NULL;
        }
        return EMPTY_STR;
    }

}
