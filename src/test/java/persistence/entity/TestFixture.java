package persistence.entity;

import persistence.entity.notcolumn.Person;

public class TestFixture {
    public static final Person person_철수 = new Person("김철수", 21, "chulsoo.kim@gmail.com", 11);
    public static final Person person_철수_id있음 = new Person(1L, "김철수", 21, "chulsoo.kim@gmail.com", 11);
    public static final Person person_영희 = new Person("김영희", 15, "younghee.kim@gmail.com", 11);
    public static final Person person_짱구 = new Person("신짱구", 15, "jjangoo.sin@gmail.com", 11);
}
