package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("클래스의 모든 필드 출력")
    void showClassFields() {
        Class<Car> carClass = Car.class;
        Field[] declaredFields = carClass.getDeclaredFields();

        Arrays.stream(declaredFields).forEach(field -> logger.info("field name: {}, type: {}", field.getName(), field.getType()));
    }

    @Test
    @DisplayName("클래스의 모든 생성자 출력")
    void showClassConstructors() {
        Class<Car> carClass = Car.class;
        Constructor<?>[] constructors = carClass.getConstructors();

        Arrays.stream(constructors).forEach(constructor -> logger.info("constructor name: {}, parameterCount: {}", constructor.getName(), constructor.getParameterCount()));
    }

    @Test
    @DisplayName("클래스의 모든 메소드 출력")
    void showClassMethods() {
        Class<Car> carClass = Car.class;
        Method[] methods = carClass.getDeclaredMethods();

        Arrays.stream(methods).forEach(method -> logger.info("method name: {}, modifiers: {}, parameterType: {}", method.getName(), method.getModifiers(), method.getParameterTypes()));
    }

    @Test
    @DisplayName("test로 시작하는 메소드 실행")
    void testMethodRun() {
        Car tesla = new Car("Tesla", 1000);
        Class<? extends Car> carClass = tesla.getClass();
        for (Method declaredMethod : carClass.getDeclaredMethods()) {
            if (declaredMethod.getName().startsWith("test")) {
                try {
                    Object invoke = declaredMethod.invoke(tesla);
                    System.out.println(invoke);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    @DisplayName("@PrintView 애노테이션 메소드 실행")
    void testAnnotationMethodRun() {
        Class<Car> carClass = Car.class;
        for (Method method : carClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(PrintView.class)) {
                try {
                    method.invoke(new Car());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    @DisplayName("private field에 값 할당")
    public void privateFieldAccess() throws IllegalAccessException {
        Car tesla = new Car("Tesla", 1000);
        assertThat(tesla.getName()).isEqualTo("Tesla");
        assertThat(tesla.getPrice()).isEqualTo(1000);

        Class<Car> carClass = Car.class;
        for (Field declaredField : carClass.getDeclaredFields()) {
            if (!declaredField.isAccessible()) {
                declaredField.setAccessible(true);
                if (declaredField.getName().equals("name")) {
                    declaredField.set(tesla, "BMW");
                } else if (declaredField.getName().equals("price")) {
                    declaredField.set(tesla, 2000);
                }
            }
        }

        assertThat(tesla.getName()).isEqualTo("BMW");
        assertThat(tesla.getPrice()).isEqualTo(2000);
    }

    @Test
    @DisplayName("클래스 이름 검증")
    void testReflectionAPI() {
        Class<Car> carClass = Car.class;
        String carClassName = carClass.getSimpleName();

        assertThat(carClassName).isEqualTo("Car");
    }

    @Test
    @DisplayName("클래스 메서드 갯수 검증")
    void testReflectionModifiers() {
        Class<Car> carClass = Car.class;
        Integer length = carClass.getDeclaredMethods().length;

        assertThat(length).isEqualTo(3);
    }

    @Test
    @DisplayName("클래스 필드 검증")
    void testExtractClassInfo() {
        Class<Car> carClass = Car.class;
        Field[] declaredFields = carClass.getDeclaredFields();
        Field declaredField = declaredFields[0];

        assertAll("infos", () -> {
            assertThat(declaredFields.length).isEqualTo(2);
                assertThat(declaredField.getName()).isEqualTo("name");
                assertThat(declaredField.getType()).isEqualTo(String.class);
            }
        );
    }
}