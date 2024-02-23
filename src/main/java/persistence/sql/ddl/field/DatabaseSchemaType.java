package persistence.sql.ddl.field;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.h2.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

public enum DatabaseSchemaType implements DatabaseSchema {
    ID(Id.class) {
        @Override
        public String getName(Field field) {
            return field.getName();
        }

        @Override
        public String getConstraints(Field field) {
            return DatabaseIdGenerationType.from(field.getAnnotation(GeneratedValue.class)).toSQL();
        }
    },
    COLUMN(Column.class) {
        @Override
        public String getName(Field field) {
            final Column annotation = field.getAnnotation(Column.class);
            if (annotation != null && !StringUtils.isNullOrEmpty(annotation.name())) {
                return annotation.name();
            }
            return field.getName();
        }

        @Override
        public String getConstraints(Field field) {
            return DatabaseConstraintType.from(field.getAnnotation(Column.class)).toSQL();
        }
    };

    private static final DatabaseSchemaType DEFAULT_SCHEMA_TYPE = DatabaseSchemaType.COLUMN;
    private final Class<? extends Annotation> jpaAnnotationClass;

    DatabaseSchemaType(Class<? extends Annotation> jpaAnnotationClass) {
        this.jpaAnnotationClass = jpaAnnotationClass;
    }

    @Override
    public String getType(Field field) {
        return DatabaseSchemaDataType.from(field.getType()).toSQL();
    }

    public static DatabaseSchemaType from(Field field) {
        return Arrays.stream(values())
                .filter(value -> field.isAnnotationPresent(value.jpaAnnotationClass))
                .findFirst()
                .orElse(DEFAULT_SCHEMA_TYPE);
    }

}
