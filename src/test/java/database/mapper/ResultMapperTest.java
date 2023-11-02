package database.mapper;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.EntityMetaData;
import persistence.sql.ddl.Person;
import persistence.sql.ddl.query.DdlQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectQueryBuilder;
import test.PersonGenerator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResultMapperTest {

    Person person = PersonGenerator.getDefualtPerson();

    @Test
    @DisplayName("resultMapper 테스트")
    void resultMapperTest() throws Exception {
        final DatabaseServer server = new H2();
        server.start();
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

        //데이터 생성
        Person person = PersonGenerator.getDefualtPerson();

        //테이블 생성
        DdlQueryBuilder ddlQueryBuilder = DdlQueryBuilder.getInstance();
        EntityMetaData entityMetaData = new EntityMetaData(person);
        jdbcTemplate.execute(ddlQueryBuilder.createTable(entityMetaData));

        //데이터 insert
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder();
        jdbcTemplate.execute(insertQueryBuilder.create(entityMetaData));
        jdbcTemplate.execute(insertQueryBuilder.create(entityMetaData));
        jdbcTemplate.execute(insertQueryBuilder.create(entityMetaData));

        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String findAll = selectQueryBuilder.findAll(entityMetaData);

        List<Person> persons = jdbcTemplate.query(findAll, new ResultMapper<>(Person.class));
        System.out.println(persons);
    }


}