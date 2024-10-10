package persistence.study;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Nested
    class 요구사항1 {

        @Test
        @DisplayName("Car 객체 정보 가져오기")
        void showClass() {
            Class<Car> carClass = Car.class;
            String builder = getClassName(carClass) +
                    getFields(carClass) +
                    getConstructors(carClass) +
                    getMethods(carClass);
            logger.debug(builder);
        }

        private String getClassName(Class<Car> clazz) {
            return "클래스명: " + clazz.getName() + System.lineSeparator();
        }

        private String getFields(Class<Car> clazz) {
            StringBuilder builder = new StringBuilder();
            builder.append("필드: ").append(System.lineSeparator());
            for (Field field : clazz.getDeclaredFields()) {
                builder.append(field.getModifiers())
                        .append(" ")
                        .append(field.getName())
                        .append(System.lineSeparator());
            }
            return builder.toString();
        }

        private String getConstructors(Class<Car> clazz) {
            StringBuilder builder = new StringBuilder();
            builder.append("생성자: ").append(System.lineSeparator());
            for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
                builder.append(constructor.getModifiers())
                        .append(" ")
                        .append(constructor.getName());
                builder.append("(")
                        .append(getParameterTypes(constructor))
                        .append(")")
                        .append(System.lineSeparator());
            }
            return builder.toString();
        }

        private String getParameterTypes(Constructor<?> constructor) {
            Class<?>[] classes = constructor.getParameterTypes();
            String[] allTypes = new String[classes.length];
            for (int i = 0; i < classes.length; i++) {
                allTypes[i] = classes[i].getName();
            }
            return String.join(", ", allTypes);
        }

        private String getMethods(Class<Car> clazz) {
            StringBuilder builder = new StringBuilder();
            builder.append("메서드: ").append(System.lineSeparator());
            for (Method method : clazz.getDeclaredMethods()) {
                builder.append(method.getModifiers()).append(" ")
                        .append(method.getName())
                        .append(System.lineSeparator());
            }
            return builder.toString();
        }
    }

    @Nested
    class 요구사항2 {

        @Test
        @DisplayName("test 로 시작하는 메소드 실행")
        void testMethodRun() {
            Car car = new Car("Dream Car", 100_000_000);
            StringBuilder builder = new StringBuilder();
            for (Method method : car.getClass().getMethods()) {
                invokeMethod(car, method)
                        .ifPresent((obj) -> builder.append(obj).append(System.lineSeparator()));
            }
            logger.debug(builder.toString());
        }

        private Optional<Object> invokeMethod(Car car, Method method) {
            if (method.getName().contains("test")) {
                return invoke(car, method);
            }
            return Optional.empty();
        }

        private Optional<Object> invoke(Car car, Method method) {
            try {
                Object returnObject = method.invoke(car);
                return Optional.of(returnObject);
            } catch (Exception exception) {
                logger.error("Method.invoke() 예외 발생", exception);
            }
            return Optional.empty();
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

            Assertions.assertAll("Car 객체 필드값 설정 검증",
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

            Assertions.assertAll("Car 객체 필드값 초기화 검증",
                    () -> assertEquals(nameField.get(car), "드림카"),
                    () -> assertEquals(priceField.get(car), 100_000_000));
        }
    }

}

