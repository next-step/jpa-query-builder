package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.text.MessageFormat;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;
        assertAll("Car 클래스의 모든 필드, 생성자, 메소드 정보 검증",
                () -> assertThat(Arrays.stream(carClass.getDeclaredFields())
                        .map(this::formatField)
                        .toList())
                        .containsExactlyInAnyOrder("private String name", "private int price"),
                () -> assertThat(Arrays.stream(carClass.getDeclaredConstructors())
                        .map(this::formatConstructor)
                        .toList())
                        .containsExactlyInAnyOrder("public persistence.study.Car(String, int)", "public persistence.study.Car()"),
                () -> assertThat(Arrays.stream(carClass.getDeclaredMethods())
                        .map(this::formatMethod)
                        .toList())
                        .containsExactlyInAnyOrder("public void printView()", "public String testGetName()", "public String testGetPrice()")
        );
    }

    private String formatField(Field field) {
        return MessageFormat.format("{0} {1} {2}",
                Modifier.toString(field.getModifiers()),
                field.getType().getSimpleName(),
                field.getName());
    }

    private String formatConstructor(Constructor<?> constructor) {
        return MessageFormat.format("{0} {1}({2})",
                Modifier.toString(constructor.getModifiers()),
                constructor.getName(),
                formatParameterTypes(constructor.getParameterTypes()));
    }

    private String formatMethod(Method method) {
        return MessageFormat.format("{0} {1} {2}({3})",
                Modifier.toString(method.getModifiers()),
                method.getReturnType().getSimpleName(),
                method.getName(),
                formatParameterTypes(method.getParameterTypes()));
    }

    private String formatParameterTypes(Class<?>[] types) {
        return String.join(", ",
                Arrays.stream(types).map(Class::getSimpleName).toList());
    }

    @Test
    @DisplayName("test 로 시작하는 메소드 실행")
    void testMethodRun() {
        Car car = new Car("Dream Car", 100_000_000);
        assertAll("메서드 실행 결과값 검증",
                () -> assertThat(Arrays.stream(car.getClass().getMethods())
                        .filter(method -> method.getName().startsWith("test"))
                        .map(method -> invokeMethod(method, car)))
                        .containsExactly("test : Dream Car", "test : 100000000")
        );
    }

    private Object invokeMethod(Method method, Car car) {
        try {
            return method.invoke(car);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Nested
    class 요구사항3 {

        @Test
        @DisplayName("@PrintView 애노테이션 메소드 실행")
        void testAnnotationMethodRun() {
            Class<Car> carClass = Car.class;
            for (Method method : carClass.getMethods()) {
                invokeMethod(carClass, method);
            }
        }

        private void invokeMethod(Class<Car> car, Method method) {
            if (method.isAnnotationPresent(PrintView.class)) {
                invoke(car, method);
            }
        }

        private void invoke(Class<Car> car, Method method) {
            try {
                method.invoke(car.getDeclaredConstructor().newInstance());
            } catch (Exception exception) {
                logger.error("Method.invoke() 예외 발생", exception);
            }
        }
    }

    @Nested
    class 요구사항4 {

        @Test
        @DisplayName("private field 에 값 할당")
        void privateFieldAccess() throws NoSuchFieldException, IllegalAccessException {
            Class<Car> carClass = Car.class;

            Field nameField = carClass.getDeclaredField("name");
            nameField.setAccessible(true);

            Field priceField = carClass.getDeclaredField("price");
            priceField.setAccessible(true);

            Car car = new Car();
            nameField.set(car, "드림카");
            priceField.set(car, 100_000_000);

            assertAll("Car 객체 필드값 설정 검증",
                    () -> assertEquals(nameField.get(car), "드림카"),
                    () -> assertEquals(priceField.get(car), 100_000_000));
        }
    }

    @Nested
    class 요구사항5 {

        @Test
        @DisplayName("인자를 가진 생성자의 인스턴스 생성")
        void constructorWithArgs() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
            Class<Car> carClass = Car.class;
            Constructor<Car> constructor = carClass.getDeclaredConstructor(String.class, int.class);
            Car car = constructor.newInstance("드림카", 100_000_000);

            Field nameField = carClass.getDeclaredField("name");
            nameField.setAccessible(true);

            Field priceField = carClass.getDeclaredField("price");
            priceField.setAccessible(true);

            assertAll("Car 객체 필드값 초기화 검증",
                    () -> assertEquals(nameField.get(car), "드림카"),
                    () -> assertEquals(priceField.get(car), 100_000_000));
        }
    }

}

