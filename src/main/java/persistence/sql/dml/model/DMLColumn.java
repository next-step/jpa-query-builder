package persistence.sql.dml.model;

public class DMLColumn {

    private final Field field;
    private final Where where;
    private final Value value;

    public DMLColumn(Class<?> clz) {
        this(new Field(clz), new Where(clz), null);
    }

    public DMLColumn(Object entity) {
        this(new Field(entity.getClass()), new Where(entity), new Value(entity));
    }

    public DMLColumn(Field field, Where where, Value value) {
        this.field = field;
        this.where = where;
        this.value = value;
    }

    public String fields() {
        return field.get();
    }

    public String whereById(Object id) {
        return where.id(id);
    }

    public String whereByEntity() {
        return where.entity(value);
    }

    public String values() {
        return value.get();
    }

}
