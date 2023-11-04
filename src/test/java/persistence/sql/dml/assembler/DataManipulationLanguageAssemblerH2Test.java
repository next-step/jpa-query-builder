package persistence.sql.dml.assembler;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.dml.DataManipulationLanguageGenerator;
import persistence.sql.usecase.GetFieldFromClassUseCase;
import persistence.sql.usecase.GetFieldValueUseCase;
import persistence.sql.usecase.GetTableNameFromClassUseCase;

class DataManipulationLanguageAssemblerH2Test {
    private final DataManipulationLanguageAssembler dataManipulationLanguageAssembler = createDataManipulationLanguageAssembler();

    @Test
    void generateInsertTest() {
        // given
        Person person = new Person("안녕하세요", 25, "tongnamuu@naver.com");

        // when
        String sql = dataManipulationLanguageAssembler.generateInsert(person);

        // then
        String expected = "insert into users (nick_name, old, email) values ('안녕하세요', 25, 'tongnamuu@naver.com');";
        assertThat(sql).isEqualTo(expected);
    }

    @Test
    void generateSelect() {
        // when
        String sql = dataManipulationLanguageAssembler.generateSelect(Person.class);

        // then
        String expected = "select * from users;";
        assertThat(sql).isEqualTo(expected);
    }

    @Test
    void generateSelectWithWhere() {
        // when
        String sql = dataManipulationLanguageAssembler.generateSelectWithWhere(Person.class);

        // then
        String expected = "select * from users where id = 1;";
        assertThat(sql).isEqualTo(expected);
    }

    @Test
    void generateDeleteWithWhere() {
        // when
        String sql = dataManipulationLanguageAssembler.generateDeleteWithWhere(Person.class);

        // then
        String expected = "delete from users where id = 1;";
        assertThat(sql).isEqualTo(expected);
    }

    private DataManipulationLanguageAssembler createDataManipulationLanguageAssembler() {
        H2Dialect h2Dialect = new H2Dialect();
        GetTableNameFromClassUseCase getTableNameFromClassUseCase = new GetTableNameFromClassUseCase();
        GetFieldFromClassUseCase getFieldFromClassUseCase = new GetFieldFromClassUseCase();
        GetFieldValueUseCase getFieldValueUseCase = new GetFieldValueUseCase();
        DataManipulationLanguageGenerator dataManipulationLanguageGenerator = new DataManipulationLanguageGenerator(
            getTableNameFromClassUseCase,
            getFieldFromClassUseCase,
            getFieldValueUseCase);
        return new DataManipulationLanguageAssembler(
            h2Dialect, dataManipulationLanguageGenerator
        );
    }
}
