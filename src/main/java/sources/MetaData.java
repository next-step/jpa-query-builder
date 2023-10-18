package sources;

import java.util.List;
import java.util.Objects;

/**
 * 테이블의 기본 정보ㄴㄴㄴㄴㄴ
 */
public class MetaData {

    private String entity;
    private String id;
    private List<String> column;

    public MetaData(String entity, String id, List<String> column) {
        this.entity = entity;
        this.id = id;
        this.column = column;
    }

    public String getEntity() {
        return entity;
    }

    public String getId() {
        return id;
    }

    public List<String> getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetaData metaData = (MetaData) o;

        if (!Objects.equals(entity, metaData.entity)) return false;
        if (!Objects.equals(id, metaData.id)) return false;
        return Objects.equals(column, metaData.column);
    }

    @Override
    public int hashCode() {
        int result = entity != null ? entity.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (column != null ? column.hashCode() : 0);
        return result;
    }
}
