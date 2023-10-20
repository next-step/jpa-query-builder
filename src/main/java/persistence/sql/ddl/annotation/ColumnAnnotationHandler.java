package persistence.sql.ddl.annotation;

import jakarta.persistence.Column;
import persistence.sql.ddl.ColumnOption;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ColumnAnnotationHandler extends AnnotationHandler<Column> {

    public ColumnAnnotationHandler(Field field) {
        super(field, Column.class);
    }

    @Override
    public List<ColumnOption> metaInfos() {

        List<ColumnOption> result = new ArrayList<>();
        if (!annotation.nullable()) {
            result.add(ColumnOption.NOT_NULL);
        }

        return result;
    }

}
