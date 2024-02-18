package persistence.reflectiontest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.")
    void showClass() {
        // given
        String expectedClassName = "persistence.reflectiontest.Car";
        List<String> expectedFieldNames = List.of("name", "price");
        List<String> expectedConstructorNames = List.of("persistence.reflectiontest.Car", "persistence.reflectiontest.Car");
        List<String> expectedMethodNames = List.of("printView", "testGetName", "testGetPrice", "getName", "getPrice");

        // when
        Class<Car> carClass = Car.class;
        String className = carClass.getName();

        List<String> fieldNames = Arrays.stream(carClass.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toList());


        List<String> constructorNames = Arrays.stream(carClass.getDeclaredConstructors())
                .map(Constructor::getName)
                .collect(Collectors.toList());


        List<String> methodNames = Arrays.stream(carClass.getDeclaredMethods())
                .map(Method::getName)
                .collect(Collectors.toList());

        // then
        logger.debug(className);
        fieldNames.forEach(logger::debug);
        constructorNames.forEach(logger::debug);
        methodNames.forEach(logger::debug);
        assertAll(
                () -> assertThat(className).isEqualTo(expectedClassName),
                () -> assertThat(fieldNames).containsExactlyInAnyOrderElementsOf(expectedFieldNames),
                () -> assertThat(constructorNames).containsExactlyInAnyOrderElementsOf(expectedConstructorNames),
                () -> assertThat(methodNames).containsExactlyInAnyOrderElementsOf(expectedMethodNames)
        );
    }

    @Test
    @DisplayName("Car 객체의 메소드 중 test로 시작하는 메소드를 실행한다.")
    void testMethodRun() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // given
        List<String> expectedResults = List.of("test : null", "test : 0");
        Class<Car> carClass = Car.class;
        Car car = carClass.getDeclaredConstructor().newInstance();

        // when
        List<Method> methods = Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .collect(Collectors.toList());

        List<String> actualResults = new ArrayList<>();
        for (Method method : methods) {
            actualResults.add((String) method.invoke(car));
        }

        // then
        assertThat(actualResults).containsExactlyInAnyOrderElementsOf(expectedResults);
    }

    @Test
    @DisplayName("@PrintView 애노테이션이 설정되어 있는 메소드를 실행한다.")
    void testAnnotationMethodRun() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // given
        final PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Class<Car> carClass = Car.class;
        Car car = carClass.getDeclaredConstructor().newInstance();
        String expectedOutput = "자동차 정보를 출력 합니다.";

        try {
            // when
            List<Method> methods = Arrays.stream(carClass.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(PrintView.class))
                    .collect(Collectors.toList());

            for (Method method : methods) {
                method.invoke(car);
            }

            // then
            assertAll(
                    () -> assertThat(methods).hasSize(1),
                    () -> assertThat(outputStream.toString().trim()).isEqualTo(expectedOutput)
            );
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("Car 클래스의 name과 price 필드에 값을 할당한 후 getter 메소드를 통해 값을 확인한다.")
    void privateFieldAccess() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // given
        Class<Car> carClass = Car.class;
        Car car = carClass.getDeclaredConstructor().newInstance();
        Map<String, Object> fieldMap = Map.of("name", "아반떼", "price", 24_000_000);

        // when
        List<Field> fields = Arrays.stream(carClass.getDeclaredFields())
                .filter(field -> Modifier.isPrivate(field.getModifiers()))
                .filter(field -> fieldMap.containsKey(field.getName()))
                .collect(Collectors.toList());

        for (Field field : fields) {
            field.setAccessible(true);
            field.set(car, fieldMap.get(field.getName()));
        }

        // then
        assertAll(
                () -> assertThat(car.getName()).isEqualTo(fieldMap.get("name")),
                () -> assertThat(car.getPrice()).isEqualTo(fieldMap.get("price"))
        );
    }

    @Test
    @DisplayName("인자를 가진 Car 인스턴스를 생성한다.")
    void constructorWithArgs() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // given
        Class<Car> carClass = Car.class;
        String name = "아반떼";
        int price = 24_000_000;

        // when
        Constructor<Car> constructor = carClass.getDeclaredConstructor(String.class, int.class);
        Car car = constructor.newInstance(name, price);

        // then
        assertAll(
                () -> assertThat(car.getName()).isEqualTo(name),
                () -> assertThat(car.getPrice()).isEqualTo(price)
        );
    }
}
