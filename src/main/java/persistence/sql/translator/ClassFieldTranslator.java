package persistence.sql.translator;

import persistence.sql.ClassComponentType;
import persistence.sql.dto.javaclass.ClassField;
import persistence.sql.dto.db.Column;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClassFieldTranslator implements ClassComponentTranslator<List<ClassField>, List<Column>> {

    private static final Map<? extends Class<? extends Serializable>, String> JAVA_CLASS_FIELD_TYPE_TO_DB_TYPE = Map.of(
            Long.class, "BIGINT",
            String.class, "VARCHAR",
            Integer.class, "BIGINT"
    );

    @Override
    public List<Column> invoke(List<ClassField> classFields) {
        return classFields.stream()
                .map(field -> new Column(field.getColumnName(), JAVA_CLASS_FIELD_TYPE_TO_DB_TYPE.get(field.getType()), field.hasIdAnnotation(), field.hasIdentityTypeGeneratedValueAnnotation(), !field.hasNotNullColumnAnnotation()))
                .collect(Collectors.toList());
    }

    @Override
    public ClassComponentType type() {
        return ClassComponentType.CLASS_FIELD;
    }
}
