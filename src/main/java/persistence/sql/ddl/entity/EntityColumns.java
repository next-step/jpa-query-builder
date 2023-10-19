package persistence.sql.ddl.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 엔티티 컬럼 정보 모음
 */
public class EntityColumns {

    private final EntityColumn idColumn;
    private final List<EntityColumn> entityColumnList = new ArrayList<>();

    public EntityColumns(Class<?> entityClass) {
        generateEntityColumns(entityClass);
        this.idColumn = findIdColumn();
    }

    /**
     * 저장할 컬럼들을  entity에서 EntityColumn으로 변환하여 entityColumns에 add (Transient 제외)
     */
    private void generateEntityColumns(Class<?> entityClass) {
        Arrays.stream(entityClass.getDeclaredFields())
                .map(EntityColumn::new)
                .filter(entityColumn -> !entityColumn.isTransient())
                .forEach(entityColumnList::add);
    }

    /**
     * ID 컬럼을 찾는다
     */
    private EntityColumn findIdColumn() {
        return entityColumnList.stream()
                .filter(EntityColumn::isId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("@Entity는 @Id 지정이 필수입니다."));
    }

    public EntityColumn getIdColumn() {
        return idColumn;
    }

    public List<EntityColumn> getEntityColumnList() {
        return entityColumnList;
    }

}
