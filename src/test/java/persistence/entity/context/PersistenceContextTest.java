package persistence.entity.context;

import entity.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Nested
@DisplayName("PersistenceContext 클래스의")
public class PersistenceContextTest {
    @Nested
    @DisplayName("manage 메소드는")
    public class manage {
        @Nested
        @DisplayName("적절한 객체가 주어지면")
        public class withValidArgs {
            @Test
            @DisplayName("영속성 컨택스트에서 관리한다.")
            void notThrow() {
                PersistencContext persistencContext = new PersistencContext();
                Person person = new Person(1L, "민준", 29, "민준.com");
                Assertions.assertDoesNotThrow(() -> persistencContext.manage(person));
            }
        }
    }

    @Nested
    @DisplayName("remove 메소드는")
    public class remove {
        @Nested
        @DisplayName("적절한 객체가 주어지면")
        public class withValidArgs {
            @Test
            @DisplayName("영속성 컨택스트에서 제거한다.")
            void notThrow() {
                PersistencContext persistencContext = new PersistencContext();
                Person person = new Person(1L, "민준", 29, "민준.com");

                Assertions.assertDoesNotThrow(() -> persistencContext.remove(person));
            }
        }
    }

    @Nested
    @DisplayName("findById 메소드는")
    public class findById {
        @Nested
        @DisplayName("적절한 클래스 정보와 아이디가 주어지면")
        public class withValidArgs {
            @Test
            @DisplayName("영속성 컨택스트에서 먼저 찾아보고 없으면 null을 리턴한다.")
            void notThrow() {
                PersistencContext persistencContext = new PersistencContext();
                Person person = new Person(1L, "민준", 29, "민준.com");
                persistencContext.manage(person);
                Person retrieved = persistencContext.findById(person.getClass(), "1");
                assertThat(retrieved.toString()).isEqualTo("Person{id=1, name='민준', age=29, email='민준.com'}");

                persistencContext.remove(person);
                Person removed = persistencContext.findById(person.getClass(), "1");
                assertThat(removed).isNull();
            }
        }
    }
}
