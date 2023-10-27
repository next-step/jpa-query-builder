package persistence.sql.ddl.annotation;

import jakarta.persistence.Id;
import persistence.sql.ddl.ColumnOption;
import persistence.sql.ddl.dialect.ColumnSchemes;
import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.dialect.Schemes;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class IdAnnotationHandler extends AnnotationHandler<Id> {

    public IdAnnotationHandler(Field field, Dialect dialect) {
        super(field, Id.class, dialect);
    }

    @Override
    public List<ColumnOption> metaInfos() {
        List<ColumnOption> result = new ArrayList<>();
        ColumnSchemes schemes = dialect.getSchemes(Schemes.Id);
        ColumnOption columnOption = ColumnOption.getColumnOption(schemes.getScheme("Id"));
        result.add(columnOption);

        return result;
    }

}
