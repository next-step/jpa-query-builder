package persistence.sql.dml;

public class InsertQueryBuilder implements DmlQueryBuilder {

    @Override
    public String create() {
        return "insert into(id, nick_name, old, email) values(1, '지영', 28, jy@lim.com)";
    }

}
