package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.lang.reflect.Field;
import java.util.StringJoiner;

public class GenerateDDL<T> {
    private final Class<T> entityClass;

    public GenerateDDL(Class<T> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("해당 클래스는 엔티티 클래스가 아닙니다.");
        }

        this.entityClass = entityClass;
    }

    public String create() {
        StringBuilder createDDL = new StringBuilder("CREATE TABLE ")
                .append(entityClass.getSimpleName())
                .append(" (");

        final Field[] fields = entityClass.getDeclaredFields();

        createDDL.append(generateFieldsDDL(fields));

        createDDL.append(")");
        return createDDL.toString();
    }

    private String generateFieldsDDL(Field[] fields) {
        JavaJdbcFiledMapper javaJdbcFiledMapper = new JavaJdbcFiledMapper();
        StringJoiner filedDDL = new StringJoiner(", ");
        for (Field field : fields) {
            EntityFiled entityFiled = new EntityFiled(field.getName(), javaJdbcFiledMapper.convert(field.getType()));
            StringBuilder builder = new StringBuilder(entityFiled.generateFiledDDL());
            if (field.isAnnotationPresent(Id.class)) {
                builder.append(" PRIMARY KEY");
            }

            filedDDL.add(builder);
        }
        return filedDDL.toString();
    }
}
