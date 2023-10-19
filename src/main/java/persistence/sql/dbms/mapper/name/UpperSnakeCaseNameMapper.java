package persistence.sql.dbms.mapper.name;

public class UpperSnakeCaseNameMapper implements NameMapper {
    @Override
    public String create(String name) {
        return SnakeCaseNameMapper.INSTANCE.create(name).toUpperCase();
    }
}
