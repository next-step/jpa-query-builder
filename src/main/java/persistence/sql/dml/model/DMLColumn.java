package persistence.sql.dml.model;

public class DMLColumn {

    private final Field field;
    private final Value value;

    public DMLColumn(Class<?> clz) {
        this(new Field(clz), null);
    }

    public DMLColumn(Object entity) {
        this(new Field(entity.getClass()), new Value(entity));
    }

    public DMLColumn(Field field, Value value) {
        this.field = field;
        this.value = value;
    }

    public Value getValue() {
        return value;
    }

    public String fields() {
        return field.getEntityFieldClause();
    }

    public String values() {
        return value.getEntityValueClause();
    }
}
