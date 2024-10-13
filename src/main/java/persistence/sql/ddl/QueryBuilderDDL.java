package persistence.sql.ddl;

import jakarta.persistence.Entity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class QueryBuilderDDL {
    private static QueryBuilderDDL queryBuilderDDL = new QueryBuilderDDL();
    private QueryBuilderDDL() { }
    static QueryBuilderDDL getInstance() {
        return queryBuilderDDL;
    }

    public String buildCreateDdl(Class<?> clazz){
        if(!clazz.isAnnotationPresent(Entity.class))
            throw new IllegalArgumentException("Entity로 정의되어 있지 않은 class를 입력하였습니다.");

        StringBuilder sb = new StringBuilder();
        sb.append("create table ");
        sb.append(getTableName(clazz)).append("(");
//        sb.append(getColumnInfos(clazz));
        sb.append(");");
        return sb.toString();
    }

    public String getTableName(Class<?> clazz) {
        return clazz.getSimpleName();
    }

    public String getColumnInfos(Class<?> clazz) throws Exception {
        StringBuilder sb = new StringBuilder();

        List<Column> columns = Arrays.stream(clazz.getDeclaredFields()).map(Column::new).collect(Collectors.toList());



        return sb.toString();
    }
}
