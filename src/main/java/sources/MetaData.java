package sources;

import java.util.List;

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
}
