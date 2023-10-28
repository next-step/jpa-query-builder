package sources;

import java.util.List;

/**
 * 테이블의 기본 정보
 */
public class MetaData {

    private String entity;
    private String id;
    private String idOption;
    private List<ColumnMetaData> columns; // 컬럼의 형, 이름

    public MetaData(String entity, String id, String idOption, List<ColumnMetaData> columns) {
        this.entity = entity;
        this.idOption = idOption;
        this.id = id;
        this.columns = columns;
    }

    public String getEntity() {
        return entity;
    }

    public String getId() {
        return id;
    }

    public String getIdOption() {
        return idOption;
    }

    public List<ColumnMetaData> getColumns() {
        return columns;
    }
}
