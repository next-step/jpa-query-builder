package persistence.sql.dml;

import persistence.dialect.Dialect;
import persistence.exception.FiledEmptyException;
import persistence.meta.EntityMeta;

public class DeleteQueryBuilder<T> extends DMLQueryBuilder<T> {
    public DeleteQueryBuilder(EntityMeta entityMeta, Dialect dialect) {
        super(entityMeta, dialect);
    }

    public String delete(Object id) {
        if (id == null) {
            throw new FiledEmptyException("id가 비어 있으면 안 됩니다.");
        }

        return delete()
                + from(entityMeta.getTableName())
                + whereId(pkColumn(), id);
    }

    private String delete() {
        return dialect.delete();
    }
}
