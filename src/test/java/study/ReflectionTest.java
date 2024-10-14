package study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ReflectionTest {
    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;

        assertThat(carClass.getName()).isEqualTo("study.Car");
    }

    @Test
    @DisplayName("test로 시작하는 메소드 실행")
    void testMethodRun() {
        Car car = new Car("자동차", 10000);
        Class<Car> carClass = Car.class;
        List<Method> testMethods = Arrays.stream(carClass.getDeclaredMethods())
            .filter(it -> it.getName().startsWith("test"))
            .toList();

        List<Object> results = testMethods.stream().map(it -> invoke(it, car)).toList();

        assertThat(results).contains("test : 자동차", "test : 10000");
    }

    @Test
    @DisplayName("@PrintView 애노테이션 메소드 실행")
    void testAnnotationMethodRun() {
        Class<Car> carClass = Car.class;
        Car car = new Car("자동차", 10000);
        List<Method> printViewMethods = Arrays.stream(carClass.getDeclaredMethods())
            .filter(it -> it.isAnnotationPresent(PrintView.class))
            .toList();

        printViewMethods.forEach(it -> invoke(it, car));
    }

    @Test
    @DisplayName("private field에 값 할당")
    void privateFieldAccess() throws NoSuchFieldException, IllegalAccessException {
        Class<Car> carClass = Car.class;
        Car car = new Car("자동차", 10000);

        Field nameField = carClass.getDeclaredField("name");
        Field priceField = carClass.getDeclaredField("price");
        nameField.setAccessible(true);
        priceField.setAccessible(true);

        nameField.set(car, "붕붕이");
        priceField.set(car, 20000);

        assertThat(car.name()).isEqualTo("붕붕이");
        assertThat(car.getPrice()).isEqualTo(20000);
    }

    @Test
    @DisplayName("인자가 없는 생성자의 인스턴스 생성")
    void constructor() throws InstantiationException, IllegalAccessException {
        Class<Car> carClass = Car.class;
        Car car = carClass.newInstance();

        assertAll(
            () -> assertThat(car.name()).isNull(),
            () -> assertThat(car.getPrice()).isZero()
        );
    }

    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스 생성")
    void constructorWithArgs() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<Car> carClass = Car.class;
        Constructor<?> constructor = Arrays.stream(carClass.getDeclaredConstructors())
            .filter(it -> it.getParameterCount() > 0)
            .findFirst().get();

        Car car = (Car) constructor.newInstance("자동차", 10000);

        assertAll(
            () -> assertThat(car.name()).isEqualTo("자동차"),
            () -> assertThat(car.getPrice()).isEqualTo(10000)
        );
    }

    private Object invoke(Method method, Car car) {
        try {
            return method.invoke(car);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
