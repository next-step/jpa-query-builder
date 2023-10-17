package persistence.sql.ddl.generator;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.dialect.H2ColumnType;
import persistence.sql.ddl.exception.RequiredAnnotationException;
import persistence.sql.ddl.generator.example.PersonV1;
import persistence.sql.ddl.generator.example.PersonV1WithNoEntityAnnotation;
import persistence.sql.ddl.generator.example.PersonV2;
import persistence.sql.ddl.generator.example.PersonV2WithColumnName;
import persistence.sql.ddl.generator.example.PersonV2WithGeneratedValue;
import persistence.sql.ddl.generator.example.PersonV2WithNotNullConstraint;
import persistence.sql.ddl.generator.example.PersonV3;
import persistence.sql.ddl.generator.example.PersonV3WithTable;
import persistence.sql.ddl.generator.example.PersonV3WithTransient;

@DisplayName("CREATE DDL 생성 테스트")
class CreateDDLQueryGeneratorTest {

    private final CreateDDLQueryGenerator createDDLQueryGenerator = new CreateDDLQueryGenerator(new H2ColumnType());

    @Nested
    @DisplayName("[요구사항 1]에 대한 엔티티에 대한 DDL을 생성할 수 있다.")
    class createDDlFromEntity_1 {
        @Test
        @DisplayName("@Entity 어노테이션이 적용된 Class 대해 DDL을 생성할 수 있다.")
        public void canCreateDDlFromEntityWithEntityAnnotation() {
            final String ddl = createDDLQueryGenerator.create(PersonV1.class);
            assertThat(ddl).isEqualTo("CREATE TABLE PERSONV1 (id bigint PRIMARY KEY, name varchar, age integer);");
        }

        @Test
        @DisplayName("@Entity 어노테이션이 적용되지 않은 Class 대해 DDL을 생성할 수 없다.")
        public void cannotCreateDDlFromEntityWithEntityAnnotation() {
            assertThatThrownBy(
                () -> createDDLQueryGenerator.create(PersonV1WithNoEntityAnnotation.class)
            ).isInstanceOf(RequiredAnnotationException.class)
                .hasMessage("@Entity annotation is required");
        }
    }

    @Nested
    @DisplayName("[요구사항 2]에 대한 엔티티에 대한 DDL을 생성할 수 있다.")
    class createDDlFromEntity_2 {

        @Test
        @DisplayName("@GeneratedValue와 @Column의 속성을 선택하여 DDL을 생성할 수 있다.")
        void canCreateDDlWithGeneratedValueAndColumn() {
            final String ddl = createDDLQueryGenerator.create(PersonV2.class);
            assertThat(ddl).isEqualTo(
                "CREATE TABLE PERSONV2 (id bigint PRIMARY KEY generated by default as identity, nick_name varchar, old integer, email varchar NOT NULL);");
        }

        @Test
        @DisplayName("@GeneratedValue의 strategy에 따라 PrimaryKey에 대한 생성 전략을 지정할 수 있다.")
        public void createDDlFromEntityWithGeneratedValueStrategy() {
            final String ddl = createDDLQueryGenerator.create(PersonV2WithGeneratedValue.class);
            assertThat(ddl).isEqualTo(
                "CREATE TABLE PERSONV2WITHGENERATEDVALUE (id bigint PRIMARY KEY generated by default as identity, name varchar, age integer, email varchar);");
        }

        @Test
        @DisplayName("@Column의 name을 통해 컬럼명을 커스텀할 수 있다.")
        public void createDDlFromEntityWithColumnName() {
            final String ddl = createDDLQueryGenerator.create(PersonV2WithColumnName.class);
            assertThat(ddl).isEqualTo(
                "CREATE TABLE PERSONV2WITHCOLUMNNAME (id bigint PRIMARY KEY, nick_name varchar, old integer, email_contact varchar);");
        }

        @Test
        @DisplayName("@Column에 Not Null 제약조건을 추가할 수 있다.")
        public void createDDlFromNotNullConstraint() {
            final String ddl = createDDLQueryGenerator.create(PersonV2WithNotNullConstraint.class);
            assertThat(ddl).isEqualTo(
                "CREATE TABLE PERSONV2WITHNOTNULLCONSTRAINT (id bigint PRIMARY KEY, name varchar, age integer, email varchar NOT NULL);");
        }
    }

    @Nested
    @DisplayName("[요구사항 3]에 대한 엔티티에 대한 DDL을 생성할 수 있다.")
    class createDDlFromEntity_3 {

        @Test
        @DisplayName("@Table과 @Transient를 적용하여 DDL을 생성할 수 있다.")
        void canCreateDDlWithTableAndTransientAnnotation() {
            final String ddl = createDDLQueryGenerator.create(PersonV3.class);
            assertThat(ddl).isEqualTo(
                "CREATE TABLE USERS (id bigint PRIMARY KEY generated by default as identity, nick_name varchar, old integer, email varchar NOT NULL);");
        }

        @Test
        @DisplayName("@Table의 속성을 지정하여 Table 이름을 수정할 수 있다.")
        void canCreateDDlWithTable() {
            final String ddl = createDDLQueryGenerator.create(PersonV3WithTable.class);
            assertThat(ddl).isEqualTo(
                "CREATE TABLE USERS (id bigint PRIMARY KEY, name varchar, age integer, email varchar);");
        }

        @Test
        @DisplayName("@Transient를 통하여 DDL 생성시 제외시킬 수 있다.")
        void canCreateDDlWithTransient() {
            final String ddl = createDDLQueryGenerator.create(PersonV3WithTransient.class);
            assertThat(ddl).isEqualTo(
                "CREATE TABLE PERSONV3WITHTRANSIENT (id bigint PRIMARY KEY, name varchar, age integer, email varchar);");
        }
    }
}