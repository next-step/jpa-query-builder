package persistence.sql.dml;

import persistence.sql.domain.Person;

public class InsertQueryBuilder {
    public String getInsertQuery(Person person) {
        return "insert into users (nick_name, old, email) VALUES ('양승인', 33, 'rhfpdk92@naver.com')";
    }
}
