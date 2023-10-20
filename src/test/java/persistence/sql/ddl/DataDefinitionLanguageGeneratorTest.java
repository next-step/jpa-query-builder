package persistence.sql.ddl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.cls.ClassWithTableAndName;
import persistence.sql.cls.ClassWithTableButEmptyName;
import persistence.sql.cls.ClassWithoutTable;
import persistence.sql.cls.ColumnTestClass;
import persistence.sql.cls.MultipleIdClass;
import persistence.sql.cls.NoEntityClass;
import persistence.sql.ddl.exception.CannotCreateTableException;
import persistence.sql.ddl.exception.FieldShouldHaveOnlyOnePrimaryKeyException;
import persistence.sql.ddl.usecase.GetFieldFromClassUseCase;
import persistence.sql.ddl.usecase.GetTableNameFromClassUseCase;
import persistence.sql.ddl.vo.DatabaseField;
import persistence.sql.ddl.vo.type.BigInt;
import persistence.sql.ddl.vo.type.Int;
import persistence.sql.ddl.vo.type.VarChar;

class DataDefinitionLanguageGeneratorTest {
    private final GetTableNameFromClassUseCase getTableNameFromClassUseCase = new GetTableNameFromClassUseCase();
    private final GetFieldFromClassUseCase getFieldFromClassUseCase = new GetFieldFromClassUseCase();
    private final DataDefinitionLanguageGenerator dataDefinitionLanguageGenerator = new DataDefinitionLanguageGenerator(
            getTableNameFromClassUseCase, getFieldFromClassUseCase
    );


    @Test
    @DisplayName("@Entity 가 없으면 CannotCreateTableException 이 발생한다.")
    void TestWithNoEntity() {
        assertThrows(
            CannotCreateTableException.class,
            () -> dataDefinitionLanguageGenerator.generateTableCreatorWithClass(NoEntityClass.class));
    }

    @Test
    @DisplayName("id가 여러개면 FieldShouldHaveOnlyOnePrimaryKeyException 이 발생한다")
    void TestWithMultipleId() {
        assertThrows(
            FieldShouldHaveOnlyOnePrimaryKeyException.class,
            () -> dataDefinitionLanguageGenerator.generateTableCreatorWithClass(MultipleIdClass.class)
        );
    }

    @Test
    @DisplayName("@Table이 없다면 TableName 은 클래스 이름이다.")
    void TestClassNameWithoutTable() {
        TableCreator tableCreator = dataDefinitionLanguageGenerator.generateTableCreatorWithClass(
            ClassWithoutTable.class);
        assertThat(tableCreator.getTableName().toString()).isEqualTo("ClassWithoutTable");
    }

    @Test
    @DisplayName("@Table이 있어도 name이 없다면 TableName 은 클래스 이름이다.")
    void TestClassNameWithTableButNoName() {
        TableCreator tableCreator = dataDefinitionLanguageGenerator.generateTableCreatorWithClass(
            ClassWithTableButEmptyName.class);
        assertThat(tableCreator.getTableName().toString()).isEqualTo("ClassWithTableButEmptyName");
    }

    @Test
    @DisplayName("@Table이 있고 name이 있다면 TableName 은 지정한 이름이다.")
    void TestClassNameWithTableAndName() {
        TableCreator tableCreator = dataDefinitionLanguageGenerator.generateTableCreatorWithClass(
            ClassWithTableAndName.class);
        assertAll(
            () -> assertThat(tableCreator.getTableName().toString()).isNotEqualTo("ClassWithTableAndName"),
            () -> assertThat(tableCreator.getTableName().toString()).isEqualTo("testClass")
        );
    }

    @Test
    @DisplayName("@Column 이 지정되지 않았다면 클래스 필드명이 테이블 필드명이 된다")
    void testFieldNameWithoutColumn() {
        TableCreator tableCreator = dataDefinitionLanguageGenerator.generateTableCreatorWithClass(
            ColumnTestClass.class);

        List<DatabaseField> databaseFields = tableCreator.getFields().getDatabaseFields();
        assertThat(databaseFields.stream().filter(it -> "noColumn".equals(it.getName())).count()).isEqualTo(1L);
    }

    @Test
    @DisplayName("@Column 이 있어도 name이 빈 값이면 클래스 필드명이 테이블 필드명이 된다")
    void testFieldNameWithColumnButNoName() {
        TableCreator tableCreator = dataDefinitionLanguageGenerator.generateTableCreatorWithClass(
            ColumnTestClass.class);

        List<DatabaseField> databaseFields = tableCreator.getFields().getDatabaseFields();
        assertThat(databaseFields.stream().filter(it -> "columnWithoutName".equals(it.getName())).count()).isEqualTo(
            1L);
    }

    @Test
    @DisplayName("@Column 이 있어도 name이 있다면 테이블 필드명이 된다")
    void testFieldNameWithColumnAndName() {
        TableCreator tableCreator = dataDefinitionLanguageGenerator.generateTableCreatorWithClass(
            ColumnTestClass.class);

        List<DatabaseField> databaseFields = tableCreator.getFields().getDatabaseFields();
        assertAll(
            () -> assertThat(databaseFields.stream().filter(it -> "columnWithName".equals(it.getName())).count()).isEqualTo(0L),
            () -> assertThat(databaseFields.stream().filter(it -> "column".equals(it.getName())).count()).isEqualTo(1L)
        );
    }


    @Test
    @DisplayName("nullable 기본값은 true이다")
    void testDefaultNullable() {
        TableCreator tableCreator = dataDefinitionLanguageGenerator.generateTableCreatorWithClass(
            ColumnTestClass.class);

        List<DatabaseField> databaseFields = tableCreator.getFields().getDatabaseFields().stream().filter(it -> "defaultNullableColumn".equals(it.getName())).collect(Collectors.toList());
        DatabaseField databaseField = databaseFields.get(0);

        assertThat(databaseField.isNullable()).isTrue();
    }

    @Test
    @DisplayName("nullable 은 @Column에서 false로 지정할 수 있다")
    void testNonNullable() {
        TableCreator tableCreator = dataDefinitionLanguageGenerator.generateTableCreatorWithClass(
            ColumnTestClass.class);

        List<DatabaseField> databaseFields = tableCreator.getFields().getDatabaseFields().stream().filter(it -> "nonNullableColumn".equals(it.getName())).collect(Collectors.toList());
        DatabaseField databaseField = databaseFields.get(0);

        assertThat(databaseField.isNullable()).isFalse();
    }

    @Test
    @DisplayName("Long은 BigInt클래스가 된다")
    void testLongType() {
        TableCreator tableCreator = dataDefinitionLanguageGenerator.generateTableCreatorWithClass(
            ColumnTestClass.class);

        List<DatabaseField> databaseFields = tableCreator.getFields().getDatabaseFields().stream().filter(it -> "fieldTypeLong".equals(it.getName())).collect(Collectors.toList());
        DatabaseField databaseField = databaseFields.get(0);

        assertThat(databaseField.getDatabaseType() == BigInt.getInstance()).isTrue();
    }

    @Test
    @DisplayName("Integer는 Int클래스가 된다")
    void testIntegerType() {
        TableCreator tableCreator = dataDefinitionLanguageGenerator.generateTableCreatorWithClass(
            ColumnTestClass.class);

        List<DatabaseField> databaseFields = tableCreator.getFields().getDatabaseFields().stream().filter(it -> "fieldTypeInteger".equals(it.getName())).collect(Collectors.toList());
        DatabaseField databaseField = databaseFields.get(0);

        assertThat(databaseField.getDatabaseType() == Int.getInstance()).isTrue();
    }

    @Test
    @DisplayName("String은 VarChar 클래스가 된다")
    void testStringType() {
        TableCreator tableCreator = dataDefinitionLanguageGenerator.generateTableCreatorWithClass(
            ColumnTestClass.class);

        List<DatabaseField> databaseFields = tableCreator.getFields().getDatabaseFields().stream().filter(it -> "fieldTypeString".equals(it.getName())).collect(Collectors.toList());
        DatabaseField databaseField = databaseFields.get(0);
        assertAll(
            () -> assertThat(databaseField.getDatabaseType() instanceof VarChar).isTrue(),
            () -> assertThat(databaseField.getDatabaseType() instanceof Int).isFalse(),
            () -> assertThat(databaseField.getDatabaseType() instanceof BigInt).isFalse()
        );
    }

    @Test
    @DisplayName("@Transient는 필드명에 포함되지 않는다.")
    void testTransientField() {
        TableCreator tableCreator = dataDefinitionLanguageGenerator.generateTableCreatorWithClass(
            ColumnTestClass.class);

        List<DatabaseField> databaseFields = tableCreator.getFields().getDatabaseFields();
        assertThat(databaseFields.stream().filter(it -> "transientField".equals(it.getName())).count()).isZero();
    }

}