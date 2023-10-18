package persistence.study;

import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static persistence.study.TestUtils.assertDoesNotThrowException;

@Nested
@DisplayName("리플랙션 라이브러리에서")
public class ReflectionTest {
    private Class<Car> carClass;

    @BeforeEach
    void before() {
        carClass = Car.class;
    }

    @Nested
    @DisplayName("컴파일된 자바 클래스의")
    class ShowClass {

        /***
         * 요구사항 1 - 클래스 정보 출력
         * src/test/java/persistence/study > Car 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.
         */
        @Nested
        @DisplayName("getName 메소드는")
        class GetName {
            @Test
            @DisplayName("자바 클래스의 이름을 반환한다.")
            void testGetName() {
                assertThat(carClass.getName()).contains("Car");
            }
        }

        @Nested
        @DisplayName("getDeclaredFields 메소드는")
        class GetDeclaredFields {
            @Test
            @DisplayName("모든 필드 배열을 반환한다.")
            void testGetDeclaredFields() {
                assertThat(
                        Stream.of(carClass.getDeclaredFields()).map(Field::getName).toArray()
                ).contains("price", "name");
            }
        }

        @Nested
        @DisplayName("getDeclaredConstructors 메소드는")
        class GetDeclaredConstructors {
            @Test
            @DisplayName("모든 생성자 배열을 반환한다.")
            void testGetDeclaredConstructors() {
                assertThat(
                        Arrays.toString(carClass.getDeclaredConstructors())
                ).contains(
                        "public persistence.study.Car(java.lang.String,int)",
                        "public persistence.study.Car()"
                );
            }
        }

        @Nested
        @DisplayName("getMethods 메소드는")
        class GetMethods {
            @Test
            @DisplayName("모든 메소드 배열을 반환한다.")
            void testGetMethods() {
                assertThat(
                        Stream.of(carClass.getMethods()).map(Method::getName).toArray()
                ).contains(
                        "testGetPrice",
                        "testGetName",
                        "printView"
                );
            }
        }

        /***
         * 요구사항 2 - test로 시작하는 메소드 실행
         * src/test/java/persistence/study > Car 객체의 메소드 중 test로 시작하는 메소드를 자동으로 실행한다. 이와 같이 Car 클래스에서 test로 시작하는 메소드만 Java Reflection을 활용해 실행하도록 구현한다.
         */
        @Nested
        @DisplayName("Method 클래스의 invoke 메소드는")
        class MethodRun {
            @Test
            @DisplayName("해당 메소드를 실행한다.")
            void testMethodRun() throws Exception {
                Car car = carClass.getDeclaredConstructor().newInstance();

                List<Method> allMethods = List.of(carClass.getDeclaredMethods());

                List<Method> testMethods = ListUtils.filter(allMethods, it -> it.getName().startsWith("test"));

                for (Method testMethod : testMethods) {
                    assertThat((String) testMethod.invoke(car)).contains("test");
                }
            }
        }

        /***
         * 요구사항 3 - @PrintView 애노테이션 메소드 실행
         * @PrintView애노테이션일 설정되어 있는 메소드를 자동으로 실행한다. 이와 같이 Car 클래스에서 @PrintView 애노테이션으로 설정되어 있는 메소드만 Java Reflection을 활용해 실행하도록 구현한다.
         */
        @Nested
        @DisplayName("Method 클래스의 isAnnotationPresent 메소드는")
        class AnnotationMethodRun {

            @Test
            @DisplayName("특정 어노테이션의 메소드를 찾아낸다.")
            void testAnnotationMethodRun() throws Exception {
                Car car = carClass.getDeclaredConstructor().newInstance();

                List<Method> allMethods = List.of(carClass.getDeclaredMethods());

                List<Method> methodWithPrintViewAnnotation = ListUtils.filter(allMethods, it -> it.isAnnotationPresent(PrintView.class));

                for (Method method : methodWithPrintViewAnnotation) {
                    assertDoesNotThrowException(() -> method.invoke(car));
                }
            }
        }

        /***
         * 요구사항 4 - private field에 값 할당
         * 자바 Reflection API를 활용해 다음 Car 클래스의 name과 price 필드에 값을 할당한 후 getter 메소드를 통해 값을 확인한다.
         */
        @Nested
        @DisplayName("Field 클래스의 setAccessible 메소드는 true 가 주어졌을 때")
        class PrivateFieldAccess {

            @Test
            @DisplayName("private 필드에 값을 할당할 수 있게한다.")
            void privateFieldAccess() throws Exception {
                Car car = carClass.getDeclaredConstructor().newInstance();

                List<Field> allFields = Stream.of(carClass.getDeclaredFields()).collect(Collectors.toList());

                for (Field field : allFields) {
                    if (field.getName().equals("name")) {
                        field.setAccessible(true);
                        field.set(car, "소나타");
                    }
                    if (field.getName().equals("price")) {
                        field.setAccessible(true);
                        field.set(car, 10);
                    }
                }

                assertThat(car.testGetName()).contains("소나타");
                assertThat(car.testGetPrice()).contains("10");
            }
        }

        /***
         * 요구사항 5 - 인자를 가진 생성자의 인스턴스 생성
         * Car 클래스의 인스턴스를 자바 Reflection API를 활용해 Car 인스턴스를 생성한다.
         */
        @Nested
        @DisplayName("getConstructor 메소드의 인자가 주어지면")
        class ConstructorWithArgs {
            @Test
            @DisplayName("인자를 가진 생성자의 인스턴스 생성한다")
            void constructorWithArgs() throws Exception {
                Car car = carClass.getConstructor(String.class, int.class).newInstance("아반떼", 100);

                Assertions.assertAll(
                        () -> assertThat(car.testGetName()).contains("아반떼"),
                        () -> assertThat(car.testGetPrice()).contains("100")
                );
            }
        }
    }
}
