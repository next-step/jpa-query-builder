package persistence.dialect;

import javax.lang.model.type.TypeKind;

public interface Dialect {

    String getPrimitiveTypeName(TypeKind typeKind);
}
