package persistence.jdbc;

import jakarta.persistence.*;
import jdbc.JdbcRowMapper;
import org.h2.tools.SimpleResultSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.meta.MetaEntity;
import persistence.sql.ddl.builder.BuilderTest;

import org.h2.tools.Csv;
import persistence.sql.fixture.PersonFixtureStep3;

import java.io.IOException;
import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class JdbcRowMapperTest {

  @Test
  @DisplayName("조회 결과를 객체로 만듭니다.")
  public void selectAndMapEntity() throws IOException, SQLException {
    String csv =
            "1L, 사이몬, 21, safasd@asmsdf.com\n";

    ResultSet rs = new Csv().read(new StringReader(csv), new String[] {"id","name", "age","email"});

    JdbcRowMapper<PersonFixtureMapper> jdbcRowMapper = new JdbcRowMapper(MetaEntity.of(PersonFixtureMapper.class));
    PersonFixtureMapper person = jdbcRowMapper.mapRow(rs);

    assertThat(person.getName()).isEqualTo("사이몬");
    assertThat(person.getId()).isEqualTo("1L");
    assertThat(person.getAge()).isEqualTo("21");
    assertThat(person.getEmail()).isEqualTo("safasd@asmsdf.com");
  }


  @Entity
  public static class PersonFixtureMapper {
    @Id
    public String id;
    public String name;
    public String age;

    public String email;

    public String getId() {
      return id;
    }

    public String getName() {
      return name;
    }

    public String getAge() {
      return age;
    }

    public String getEmail() {
      return email;
    }
  }
}


