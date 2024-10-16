package persistence.sql.ddl;

import org.jetbrains.annotations.NotNull;
import persistence.sql.node.FieldNode;

/**
 * 쿼리 제공자
 */
public interface QuerySupplier extends Comparable<QuerySupplier> {

    /**
     * 제공자 우선순위
     */
    short priority();

    /**
     * 제공자 지원 여부를 반환한다.
     *
     * @param fieldNode 필드 노드
     */
    boolean supported(FieldNode fieldNode);

    /**
     * 필드 노드를 기반으로 쿼리를 생성해 반환한다.
     *
     * @param fieldNode 필드 노드
     */
    String supply(FieldNode fieldNode);

    @Override
    default int compareTo(@NotNull QuerySupplier o) {
        return Short.compare(priority(), o.priority());
    }
}
