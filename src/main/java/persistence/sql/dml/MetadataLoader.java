package persistence.sql.dml;

import persistence.sql.common.util.NameConverter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Predicate;

/**
 * 메타데이터를 로드하는 인터페이스
 */
public interface MetadataLoader<T> {

    /**
     * 특정 인덱스의 필드를 조회해 반환한다.
     *
     * @param index 인덱스
     */
    Field getField(int index);

    /**
     * 테이블명을 조회해 반환한다.
     */
    String getTableName();

    /**
     * 엔티티명을 조회해 반환한다.
     */
    String getEntityName();

    /**
     * 컬럼명을 조회해 반환한다.
     */
    String getColumnName(int index, NameConverter nameConverter);

    String getColumnName(Field field, NameConverter nameConverter);

    /**
     * 필드명을 조회해 반환한다.
     */
    String getFieldName(int index);

    /**
     * 클래스의 모든 필드명을 조회해 반환한다.
     */
    List<String> getFieldNameAll();

    /**
     * 클래스의 모든 컬럼명을 조회해 반환한다.
     */
    List<String> getColumnNameAll(NameConverter nameConverter);

    /**
     * 기본키 컬럼명을 조회해 반환한다.
     */
    Field getPrimaryKeyField();

    /**
     * 컬럼의 개수를 조회해 반환한다.
     */
    int getColumnCount();


    /**
     * 조건식을 만족하는 필드 목록을 조회해 반환한다.
     *
     * @param fieldPredicate 조건식
     */
    List<Field> getFieldAllByPredicate(Predicate<Field> fieldPredicate);

    /**
     * 인스턴스를 생성하는 생성자를 조회해 반환한다.
     */
    Constructor<T> getNoArgConstructor();

    /**
     * 엔티티 타입을 조회해 반환한다.
     */
    Class<T> getEntityType();
}
