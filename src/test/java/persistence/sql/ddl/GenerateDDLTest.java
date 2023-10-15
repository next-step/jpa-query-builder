package persistence.sql.ddl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GenerateDDLTest {

    @Test
    @DisplayName("엔티티 어노테이션이 붙지 않은 엔티티 클래스는 예외가 발생한다.")
    void noEntity() {
        Class<NoEntityPerson> noEntityPersonClass = NoEntityPerson.class;

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new GenerateDDL<>(noEntityPersonClass));
    }

    @Test
    @DisplayName("요구사항 1 - @id를 가진 create 쿼리 만들기 ")
    void pkByCreateQuery() {
        GenerateDDL<PkPerson> pkPersonGenerateDDL = new GenerateDDL<>(PkPerson.class);

        String sql = pkPersonGenerateDDL.create();

        assertThat(sql).isEqualTo("CREATE TABLE PkPerson ("
                + "id bigint PRIMARY KEY, "
                + "name varchar(255), "
                + "age integer"
                + ")"
        );

    }

}
