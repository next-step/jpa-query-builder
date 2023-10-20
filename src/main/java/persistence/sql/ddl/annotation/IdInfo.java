package persistence.sql.ddl.annotation;

import jakarta.persistence.Id;
import persistence.sql.ddl.ColumnMetaInfo;

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
    public List<ColumnMetaInfo> getColumnMetaInfos() {
        List<ColumnMetaInfo> columnMetaInfos = new ArrayList<>();
        columnMetaInfos.add(new ColumnMetaInfo("PRIMARY KEY", 2));

        return columnMetaInfos;
    }

}
