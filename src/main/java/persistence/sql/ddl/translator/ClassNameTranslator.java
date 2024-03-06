package persistence.sql.ddl.translator;

import persistence.sql.ddl.ClassComponentType;
import persistence.sql.ddl.dto.javaclass.ClassName;
import persistence.sql.ddl.dto.db.Table;

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
