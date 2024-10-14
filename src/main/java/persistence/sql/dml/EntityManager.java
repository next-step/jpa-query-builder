package persistence.sql.dml;

import java.util.List;

public interface EntityManager {
    /**
     * 엔티티를 저장한다.
     * @param entity 저장할 엔티티
     */
    <T> void persist(T entity);

    /**
     * 엔티티를 수정한다.
     * @param entity 수정할 엔티티
     */
    <T> void merge(T entity);

    /**
     * 엔티티를 삭제한다.
     * @param entity 삭제할 엔티티
     */
    <T> void remove(T entity);

    /**
     * 엔티티를 조회한다.
     * @param returnType 조회할 엔티티 클래스
     * @param primaryKey 조회할 엔티티의 기본 키
     * @return 조회된 엔티티
     */
    <T> T find(Class<T> returnType,  Object primaryKey);

    /**
     * 엔티티 목록을 조회한다.
     * @param entityClass 조회할 엔티티 클래스
     * @return 조회된 엔티티 목록
     */
    <T> List<T> findAll(Class<T> entityClass);
}
