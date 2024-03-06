package persistence.sql.translator;

import persistence.sql.ClassComponentType;
import persistence.sql.dto.javaclass.ClassName;
import persistence.sql.dto.db.Table;

public class ClassNameTranslator implements ClassComponentTranslator<ClassName, Table> {

    @Override
    public Table invoke(ClassName className) {
        return new Table(className.getTableName());
    }

    @Override
    public ClassComponentType type() {
        return ClassComponentType.CLASS_NAME;
    }
}
