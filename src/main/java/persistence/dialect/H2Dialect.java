package persistence.dialect;

public class H2Dialect implements Dialect {

    public static final String QUOTE = "`\"[";
    public static final String CLOSED_QUOTE = "`\"]";

    public String transferType(String type) {
        switch ( type ) {
            case "Long":
                return "bigint";
            case "int":
                return "int";
            case "String":
                return "varchar";
            default:
                return "";
        }
    }

}
