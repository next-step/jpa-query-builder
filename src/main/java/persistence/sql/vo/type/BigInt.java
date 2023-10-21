package persistence.sql.vo.type;

public class BigInt implements DatabaseType {
    private static final BigInt instance = new BigInt();
    private BigInt() {
    }

    public static BigInt getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "bigint";
    }
}
