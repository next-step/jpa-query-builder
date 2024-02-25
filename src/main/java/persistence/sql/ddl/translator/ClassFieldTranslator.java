package persistence.sql.ddl.translator;

import persistence.sql.ddl.ClassComponentType;
import persistence.sql.ddl.dto.javaclass.ClassField;
import persistence.sql.ddl.dto.db.DBColumn;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClassFieldTranslator implements ClassComponentTranslator<ClassField, DBColumn> {

    private static final Map<? extends Class<? extends Serializable>, String> JAVA_CLASS_FIELD_TYPE_TO_DB_TYPE = Map.of(
            Long.class, "BIGINT",
            String.class, "VARCHAR",
            Integer.class, "BIGINT"
    );

    @Override
    public List<DBColumn> invoke(List<ClassField> classFields) {
        return classFields.stream()
                .map(field -> new DBColumn(field.getName(), JAVA_CLASS_FIELD_TYPE_TO_DB_TYPE.get(field.getType()), field.isIdAnnotationPresent()))
                .collect(Collectors.toList());
    }

    @Override
    public ClassComponentType type() {
        return ClassComponentType.CLASS_FIELD;
    }
}
