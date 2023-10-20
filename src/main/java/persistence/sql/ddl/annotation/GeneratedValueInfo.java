package persistence.sql.ddl.annotation;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import persistence.sql.ddl.ColumnOption;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class GeneratedValueInfo implements AnnotationInfo {

    private GeneratedValue generatedValue;

    public GeneratedValueInfo(Field field) {
        initialize(field);
    }

    @Override
    public void initialize(Field field) {
        this.generatedValue = field.getAnnotation(GeneratedValue.class);
    }

    @Override
    public List<ColumnOption> metaInfos() {
        List<ColumnOption> result = new ArrayList<>();
        if (generatedValue.strategy().name().equals(GenerationType.IDENTITY.name())) {
            result.add(ColumnOption.AUTO_INCREMENT);
        }

        return result;
    }

}
