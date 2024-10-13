package persistence.sql.ddl;

import persistence.sql.node.EntityNode;

/**
 * 쿼리 빌더
 */
public interface QueryBuilder {
    /**
     * 엔티티 노드를 기반으로 테이블 생성 쿼리를 생성해 반환한다.
     * @param entityNode 엔티티 노드
     */
    String buildCreateTableQuery(EntityNode<?> entityNode);

    /**
     * 엔티티 노드를 기반으로 테이블 삭제 쿼리를 생성해 반환한다.
     *
     * @param entityNode 엔티티 노드
     */
    String buildDropTableQuery(EntityNode<?> entityNode);

    <T> T buildInsertQuery(EntityNode<T> entityNode)
}
