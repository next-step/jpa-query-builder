package persistence.sql.ddl.annotation;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import persistence.sql.ddl.ColumnOption;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class GeneratedValueAnnotationHandler extends AnnotationHandler<GeneratedValue> {

    public GeneratedValueAnnotationHandler(Field field) {
        super(field, GeneratedValue.class);
    }

    @Override
    public List<ColumnOption> metaInfos() {

        List<ColumnOption> result = new ArrayList<>();
        if (annotation.strategy().name().equals(GenerationType.IDENTITY.name())) {
            result.add(ColumnOption.AUTO_INCREMENT);
        }

        return result;
    }

}
