package sources;

import java.util.Map;

/**
 * 테이블의 기본 정보ㄴㄴㄴㄴㄴ
 */
public class MetaData {

    private String entity;
    private String id;
    private Map<String, String> columns; // 컬럼의 형, 이름

    public MetaData(String entity, String id, Map<String, String> columns) {
        this.entity = entity;
        this.id = id;
        this.columns = columns;
    }

    public String getEntity() {
        return entity;
    }

    public String getId() {
        return id;
    }

    public Map<String, String> getColumns() {
        return columns;
    }
}
