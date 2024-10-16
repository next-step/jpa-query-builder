package orm.dsl.dialect.h2;

import orm.TableEntity;
import orm.dsl.dml.InsertImpl;

public class H2InsertImpl<ENTITY> extends InsertImpl<ENTITY> {

    public H2InsertImpl(TableEntity<ENTITY> tableEntity) {
        super(tableEntity);
    }
}
