package persistence.sql.ddl.annotation;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import persistence.sql.ddl.ColumnOption;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class GeneratedValueInfo extends AnnotationInfo {

    public GeneratedValueInfo(Field field) {
        super(field);
    }

    @Override
    protected Class<GeneratedValue> getAnnotationType() {
        return GeneratedValue.class;
    }

    @Override
    public List<ColumnOption> metaInfos() {
        GeneratedValue generatedValue = (GeneratedValue) super.annotation;

        List<ColumnOption> result = new ArrayList<>();
        if (generatedValue.strategy().name().equals(GenerationType.IDENTITY.name())) {
            result.add(ColumnOption.AUTO_INCREMENT);
        }

        return result;
    }

}
