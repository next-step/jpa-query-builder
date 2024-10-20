package builder.ddl.dataType;


public interface DataType {

    // dataType으로 H2DataType을 찾고 반환하는 메소드
    String findDataTypeByClass(Class<?> dataType);

}
