package service.person;

import builder.QueryBuilderDML;
import entity.Person;
import jdbc.EntityMapper;
import jdbc.JdbcTemplate;
import service.person.request.PersonRequest;
import service.person.response.PersonResponse;

import java.util.List;

public class PersonService {

    private final JdbcTemplate jdbcTemplate;
    private final QueryBuilderDML queryBuilderDML;

    public PersonService(JdbcTemplate jdbcTemplate, QueryBuilderDML queryBuilderDML) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryBuilderDML = queryBuilderDML;
    }

    //Person을 저장한다.
    public void save(PersonRequest personRequest) {
        jdbcTemplate.execute(queryBuilderDML.buildInsertQuery(personRequest.toEntity()));
    }

    //Person을 가져온다.
    public List<PersonResponse> findAll() {
        List<Person> personList = jdbcTemplate.query(queryBuilderDML.buildFindAllQuery(Person.class), resultSet -> EntityMapper.mapRow(resultSet, Person.class));
        return personList.stream().map(PersonResponse::of).toList();
    }

    public PersonResponse findById(Long id) {
        Person person = jdbcTemplate.queryForObject(queryBuilderDML.buildFindByIdQuery(Person.class, id), resultSet -> EntityMapper.mapRow(resultSet, Person.class));
        return PersonResponse.of(person);
    }

    public void deleteById(Long id) {
        jdbcTemplate.execute(queryBuilderDML.buildDeleteByIdQuery(Person.class, id));
    }

    public void deleteAll() {
        jdbcTemplate.execute(queryBuilderDML.buildDeleteQuery(Person.class));
    }
}
