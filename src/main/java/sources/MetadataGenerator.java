package sources;

public interface MetadataGenerator {

    // 엔티티 클래스로 메타데이터를 만드는 메소드
    MetaData generator(Class<?> entity);
}
