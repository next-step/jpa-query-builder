package persistence.sql.vo.type;

public class Int implements DatabaseType {
    private static final Int instance = new Int();
    private Int() {
    }

    public static Int getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "int";
    }
}
