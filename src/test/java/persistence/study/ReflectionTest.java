package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.stury.Car;
import persistence.stury.PrintView;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @DisplayName("Car 객체 정보 가져오기")
    @Test
    void showClass() {
        final Class<Car> clazz = Car.class;
        logger.debug(clazz.getName());

        assertThat(clazz.getSimpleName()).isEqualTo("Car");
    }

    @DisplayName("Car 객체에 있는 test로 시작하는 메서드들을 실행한다.")
    @Test
    void runTestMethods() throws Exception {

        final Class<Car> clazz = Car.class;
        final Car instance = clazz.getConstructor().newInstance();
        final Method[] methods = clazz.getMethods();

        final Stream<Object> results = Arrays.stream(methods)
                .filter(method -> method.getName().startsWith("test"))
                .map(method -> {
                    try {
                        return method.invoke(instance);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

        assertThat(results).hasSize(2)
                .containsAnyOf("test : null", "test : 0");
    }

    @DisplayName("Car 객체에 있는 PrintView 어노테이션이 적용된 메서드들을 실행한다.")
    @Test
    void runMethodsWithPrintViewAnnotation() throws Exception {
        final PrintStream printStream = System.out;
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        final Class<Car> clazz = Car.class;
        final Car instance = clazz.getConstructor().newInstance();
        final Method[] methods = clazz.getMethods();

        Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .forEach(method -> {
                    try {
                        method.invoke(instance);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

        System.setOut(printStream);

        assertThat(outputStream.toString().trim()).isEqualTo("자동차 정보를 출력 합니다.");
    }

    @DisplayName("Car 객체에 있는 private 필드에 값을 set한다.")
    @Test
    void setPrivateFields() throws Exception {

        final String name = "자동차";
        final int price = 1000;

        final Class<Car> clazz = Car.class;
        final Car instance = clazz.getConstructor().newInstance();
        final Field[] fields = clazz.getDeclaredFields();

        Arrays.stream(fields)
                .forEach(field -> {
                    try {
                        field.setAccessible(true);
                        if (field.getName().equals("name")) field.set(instance, name);
                        else if (field.getName().equals("price")) field.set(instance, price);
                    } catch (Exception e) {
                        throw new RuntimeException();
                    }
                });

        assertThat(instance.getName()).isEqualTo(name);
        assertThat(instance.getPrice()).isEqualTo(price);
    }

    @DisplayName("인자가 있는 Car 생성자를 사용해 객체를 생성한다.")
    @Test
    void createInstanceWithArgsConstructor() throws Exception {

        final String name = "자동차";
        final int price = 1000;

        final Class<Car> clazz = Car.class;
        final Constructor<?>[] carConstructors = clazz.getConstructors();

        final Object result = Arrays.stream(carConstructors)
                .filter(constructor -> constructor.getParameterCount() == 2)
                .findFirst()
                .orElseThrow()
                .newInstance(name, price);


        assertThat(result).isInstanceOf(Car.class);
        assertThat(((Car) result).getName()).isEqualTo(name);
        assertThat(((Car) result).getPrice()).isEqualTo(price);
    }
}
