package persistence.sql.ddl;

import persistence.annotations.Entity;
import persistence.common.EntityField;
import persistence.sql.QueryBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CreateQueryBuilder implements QueryBuilder {

    @Override
    public String build(Object obj) {
        Class<?> clazz = obj.getClass();
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new NoEntityException(clazz.getName());
        }

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ");
        sb.append(clazz.getSimpleName() + "(\n");

        List<EntityField> efs = Arrays.stream(clazz.getDeclaredFields())
                .map(EntityField::new)
                .collect(Collectors.toList());

        efs.stream().forEach(ef -> sb.append(ef.getCreateFieldQuery()));

        sb.append("PRIMARY KEY(");
        efs.stream().filter(ef -> ef.isPk()).forEach(ef -> sb.append(ef.getName()));
        sb.append(")\n");
        sb.append(");\n");

        return sb.toString();
    }
}
