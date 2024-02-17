package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @DisplayName("Car 객체 정보 가져오기")
    @Test
    void showClass() {
        final Class<Car> carClass = Car.class;

        Arrays.stream(carClass.getDeclaredFields()).forEach(System.out::println);
        Arrays.stream(carClass.getConstructors()).forEach(System.out::println);
        Arrays.stream(carClass.getDeclaredMethods()).forEach(System.out::println);
    }

    @DisplayName("test로 시작하는 메소드 실행한다.")
    @Test
    void testMethodRun() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final Class<Car> carClass = Car.class;
        final Car car = carClass.getDeclaredConstructor().newInstance();

        for (Method method : carClass.getDeclaredMethods()) {
            if (method.getName().startsWith("test")) {
                final String result = (String) method.invoke(car);
                logger.info(result);
            }
        }
    }

    @DisplayName("@PrintView 애노테이션 메소드 실행")
    @Test
    void testAnnotationMethodRun() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final Class<Car> carClass = Car.class;
        final Car car = carClass.getConstructor().newInstance();

        for (Method method : carClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(PrintView.class)) {
                method.invoke(car);
            }
        }
    }

    @DisplayName("private field에 값 할당")
    @Test
    void privateFieldAccess() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final String name = "simpson";
        final int price = 100;
        final Class<Car> carClass = Car.class;
        final Car car = carClass.getConstructor().newInstance();

        for (Field field : carClass.getDeclaredFields()) {
            if (field.getModifiers() == Modifier.PRIVATE) {
                field.setAccessible(true);

                if (field.getName().equals("name")) {
                    field.set(car, name);
                }
                if (field.getName().equals("price")) {
                    field.set(car, price);
                }
            }
        }
        assertThat(car.getName()).isEqualTo(name);
        assertThat(car.getPrice()).isEqualTo(price);
    }

    @DisplayName("인자를 가진 생성자의 인스턴스 생성")
    @Test
    void constructorWithArgs() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final String name = "simpson";
        final int price = 100;
        final Class<Car> carClass = Car.class;
        final Car car = carClass.getConstructor(String.class, int.class).newInstance(name, price);

        assertThat(car).isEqualTo(new Car(name, 100));
    }
}
