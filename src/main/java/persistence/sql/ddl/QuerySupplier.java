package persistence.sql.ddl;

import org.jetbrains.annotations.NotNull;
import persistence.sql.ddl.node.FieldNode;

public interface QuerySupplier extends Comparable<QuerySupplier> {
    short priority();
    boolean supported(FieldNode fieldNode);
    String supply(FieldNode fieldNode);

    @Override
    default int compareTo(@NotNull QuerySupplier o) {
        return Short.compare(priority(), o.priority());
    }
}
