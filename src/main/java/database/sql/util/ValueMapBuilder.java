package database.sql.util;

import database.sql.Person;

import java.util.HashMap;
import java.util.Map;

public class ValueMapBuilder {

    private final EntityClassInspector entityClassInspector;

    public ValueMapBuilder(EntityClassInspector entityClassInspector) {
        this.entityClassInspector = entityClassInspector;
    }

    public Map<String, Object> buildMap(Object entity) {
        // XXX class 와 entity 를 참조해서 수정하기
        Person person = (Person) entity;
        Map<String, Object> map = new HashMap<>();
        map.put("nick_name", person.getName());
        map.put("old", person.getAge());
        map.put("email", person.getEmail());
        return map;
    }
}
