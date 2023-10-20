package persistence.sql.ddl;

import persistence.annotations.GenerationType;
import persistence.common.EntityClazz;
import persistence.common.FieldClazz;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CreateQueryBuilder {

    public String getQuery(Class<?> clazz) {
        EntityClazz entityClazz = new EntityClazz(clazz);

        StringBuilder sb = new StringBuilder();
        sb.append(getCreateEntityQuery(entityClazz));

        List<FieldClazz> efs = Arrays.stream(clazz.getDeclaredFields())
                .map(FieldClazz::new)
                .collect(Collectors.toList());

        efs.stream().filter(FieldClazz::notTransient)
                .forEach(ef -> sb.append(getCreateFieldQuery(ef)));

        sb.append("PRIMARY KEY(");
        efs.stream().filter(ef -> ef.isPk()).forEach(ef -> sb.append(ef.getName()));
        sb.append(")\n");
        sb.append(");\n");

        return sb.toString();
    }

    private String getCreateEntityQuery(EntityClazz entityClazz) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ");
        sb.append(entityClazz.getName() + "(\n");
        return sb.toString();
    }

    private String getCreateFieldQuery(FieldClazz fieldClazz) {
        StringBuilder sb = new StringBuilder();
        sb.append(fieldClazz.getName());
        sb.append(" ");
        sb.append(getType(fieldClazz.getClazz()));
        sb.append(" ");
        if (!fieldClazz.isNullable()) {
            sb.append("NOT NULL");
        }
        if (fieldClazz.getGenerationType() != null) {
            sb.append(getGenerationTypeQuery(fieldClazz.getGenerationType()));
        }

        sb.append(",\n");
        return sb.toString();
    }

    private String getType(Class<?> clazz) {
        if (clazz.equals(Long.class)) {
            return "BIGINT";
        } else if (clazz.equals(Integer.class)) {
            return "INT";
        } else if (clazz.equals(String.class)) {
            return "VARCHAR(50)";
        }

        return null;
    }

    private String getGenerationTypeQuery(GenerationType generationType) {
        if (generationType.equals(GenerationType.IDENTITY)) {
            return "generated by default as identity";
        }

        return null;
    }
}
