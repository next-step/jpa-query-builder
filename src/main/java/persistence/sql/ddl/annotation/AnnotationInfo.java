package persistence.sql.ddl.annotation;

import persistence.sql.ddl.ColumnMetaInfo;

import java.lang.reflect.Field;
import java.util.List;

public interface AnnotationInfo {

    void initialize(Field field);

    List<ColumnMetaInfo> getColumnMetaInfos();

}
