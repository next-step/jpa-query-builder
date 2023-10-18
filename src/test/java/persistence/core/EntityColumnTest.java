package persistence.core;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import domain.FixtureEntity;

import java.lang.reflect.Field;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class EntityColumnTest {

    private Class<?> mockClass;

    @Test
    @DisplayName("Id 컬럼 정보로 EntityColumn 인스턴스를 생성 할 수 있다.")
    void testEntityColumnWithId() throws Exception {
        mockClass = FixtureEntity.WithId.class;
        final Field field = mockClass.getDeclaredField("id");
        final EntityColumn column = new EntityColumn(field);
        assertResult(column, "id", "id" , true, true, false, false, Long.class);
    }

    @Test
    @DisplayName("Id 컬럼인데 Column 설정으로 이름을 설정해 EntityColumn 인스턴스를 생성 할 수 있다.")
    void testEntityColumnWithIdAndColumn() throws Exception {
        mockClass = FixtureEntity.WithIdAndColumn.class;
        final Field field = mockClass.getDeclaredField("id");
        final EntityColumn column = new EntityColumn(field);
        assertResult(column, "test_id", "id" , true, true, false, false, Long.class);
    }

    @Test
    @DisplayName("Id 컬럼의 GeneratedValue 를 이용하면 not null 과 autoIncrement 값이 true 인 EntityColumn 인스턴스를 생성 할 수있다.")
    void testEntityColumnWithIdGeneratedValue() throws Exception {
        mockClass = FixtureEntity.IdWithGeneratedValue.class;
        final Field field = mockClass.getDeclaredField("id");
        final EntityColumn column = new EntityColumn(field);
        assertResult(column, "id", "id" , true, true, true, false, Long.class);
    }

    @Test
    @DisplayName("일반 필드를 이용해 EntityColumn 인스턴스를 생성 할 수 있다.")
    void testEntityColumnWithoutColumn() throws Exception {
        mockClass = FixtureEntity.WithoutColumn.class;
        final Field field = mockClass.getDeclaredField("column");
        final EntityColumn column = new EntityColumn(field);
        assertResult(column, "column", "column" , false, false, false, true, String.class);
    }

    @Test
    @DisplayName("일반 필드에 @Cloumn 을 이용해 이름 설정해 EntityColumn 인스턴스를 생성 할 수 있다.")
    void testEntityColumnWithColumn() throws Exception {
        mockClass = FixtureEntity.WithColumn.class;
        final Field field = mockClass.getDeclaredField("column");
        final EntityColumn column = new EntityColumn(field);
        assertResult(column, "test_column", "column" , false, false, false, true, String.class);
    }

    @Test
    @DisplayName("일반 필드에 @Cloumn 을 이용해 NotNull 이 true 인 EntityColumn 인스턴스를 생성 할 수 있다.")
    void testEntityColumnWithColumnNonNull() throws Exception {
        mockClass = FixtureEntity.WithColumn.class;
        final Field field = mockClass.getDeclaredField("notNullColumn");
        final EntityColumn column = new EntityColumn(field);
        assertResult(column, "notNullColumn", "notNullColumn" , false, true, false, true, String.class);
    }

    @Test
    @DisplayName("일반 필드에 @Cloumn 을 이용해 insertable 이 false 인 EntityColumn 인스턴스를 생성 할 수 있다.")
    void testEntityColumnWithColumnNonInsertable() throws Exception {
        mockClass = FixtureEntity.WithColumnNonInsertable.class;
        final Field field = mockClass.getDeclaredField("notInsertableColumn");
        final EntityColumn column = new EntityColumn(field);
        assertResult(column, "notInsertableColumn", "notInsertableColumn" , false, false, false, false, String.class);
    }

    @Test
    @DisplayName("Id 컬럼에는 @Cloumn 의 insertable 이 작동하지 않으며 항상 false 를 리턴한다.")
    void testEntityColumnWithIdNonInsertableNotWorking() throws Exception {
        mockClass = FixtureEntity.WithIdInsertable.class;
        final Field field = mockClass.getDeclaredField("id");
        final EntityColumn column = new EntityColumn(field);
        assertResult(column, "id", "id" , true, true, false, false, Long.class);
    }

    private void assertResult(final EntityColumn result,
                              final String columnName,
                              final String fieldName,
                              final boolean isId,
                              final boolean isNotNull,
                              final boolean isAutoIncrement,
                              final boolean isInsertable,
                              final Class<?> type) {
        assertSoftly(softly -> {
            softly.assertThat(result.getName()).isEqualTo(columnName);
            softly.assertThat(result.getFieldName()).isEqualTo(fieldName);
            softly.assertThat(result.isId()).isEqualTo(isId);
            softly.assertThat(result.isNotNull()).isEqualTo(isNotNull);
            softly.assertThat(result.isAutoIncrement()).isEqualTo(isAutoIncrement);
            softly.assertThat(result.isInsertable()).isEqualTo(isInsertable);
            softly.assertThat(result.getType()).isEqualTo(type);
        });

    }

}
