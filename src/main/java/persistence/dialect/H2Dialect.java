package persistence.dialect;

import javax.lang.model.type.TypeKind;

public class H2Dialect implements Dialect {

    public static final String QUOTE = "`\"[";
    public static final String CLOSED_QUOTE = "`\"]";

    public String getPrimitiveTypeName(TypeKind kind) {
        switch ( kind ) {
            case LONG:
                return "bigint";
            case INT:
                return "int";
            default:
                return "varchar";
        }
    }
}
