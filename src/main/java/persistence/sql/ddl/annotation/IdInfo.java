package persistence.sql.ddl.annotation;

import jakarta.persistence.Id;
import persistence.sql.ddl.ColumnOption;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class IdInfo implements AnnotationInfo {

    private Id id;

    public IdInfo(Field field) {
        initialize(field);
    }

    @Override
    public void initialize(Field field) {
        this.id = field.getAnnotation(Id.class);
    }

    @Override
    public List<ColumnOption> metaInfos() {
        List<ColumnOption> result = new ArrayList<>();
        result.add(ColumnOption.PRIMARY_KEY);

        return result;
    }

}
