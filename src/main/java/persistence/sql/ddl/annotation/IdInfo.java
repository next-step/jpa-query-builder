package persistence.sql.ddl.annotation;

import jakarta.persistence.Id;
import persistence.sql.ddl.ColumnOption;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class IdInfo extends AnnotationInfo {

    public IdInfo(Field field) {
        super(field);
    }

    @Override
    protected Class<Id> getAnnotationType() {
        return Id.class;
    }

    @Override
    public List<ColumnOption> metaInfos() {
        List<ColumnOption> result = new ArrayList<>();
        result.add(ColumnOption.PRIMARY_KEY);

        return result;
    }

}
