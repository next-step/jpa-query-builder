package persistence.sql.ddl.annotation;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import persistence.sql.ddl.ColumnMetaInfo;

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
    public List<ColumnMetaInfo> getColumnMetaInfos() {
        List<ColumnMetaInfo> columnMetaInfos = new ArrayList<>();

        if (generatedValue.strategy().name().equals(GenerationType.IDENTITY.name())) {
            columnMetaInfos.add(new ColumnMetaInfo("AUTO_INCREMENT", 1));
        }

        return columnMetaInfos;
    }

}
