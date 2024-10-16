package persistence.sql.ddl;

import persistence.sql.node.EntityNode;

import java.util.Set;

/**
 * 테이블 스캐너
 */
public interface TableScanner {

    /**
     * 매개변수로 전달받은 패키지 경로를 기반으로 테이블 노드를 스캔한다.
     *
     * @param basePackage 패키지 경로
     * @return 테이블 노드 집합
     */
    Set<EntityNode<?>> scan(String basePackage);
}
