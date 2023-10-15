package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReflectTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectTest.class);

    @Test
    @DisplayName("Car 객체 정보를 가져와 출력한다.")
    void showClass() {
        Class<Car> carClass = Car.class;
        logger.debug(Arrays.toString(carClass.getDeclaredFields()));
        logger.debug(Arrays.toString(carClass.getDeclaredConstructors()));
        logger.debug(Arrays.toString(carClass.getDeclaredMethods()));
    }

    @Test
    @DisplayName("test로 시작하는 메소드를 실행한다.")
    void testMethodRun() throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<Car> carClass = Car.class;
        List<Method> methods = Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .toList();
        for (Method method : methods) {
            method.invoke(carClass.newInstance());
        }
    }

    @Test
    @DisplayName("@PrintView 애노테이션이 표시된 메소드를 실행한다.")
    void testAnnotationMethodRun() throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<Car> carClass = Car.class;
        List<Method> methods = Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .toList();
        for (Method method : methods) {
            method.invoke(carClass.newInstance());
        }
    }

    @Test
    @DisplayName("Private Field에 값을 할당한다.")
    void privateFieldAccess() throws IllegalAccessException, NoSuchFieldException {
        Car car = new Car("S60", 5000);

        Field name = car.getClass().getDeclaredField("name");
        name.setAccessible(true);
        name.set(car, "BMW 3 Series");

        assertThat(car.getName()).isEqualTo("BMW 3 Series");

        Field price = car.getClass().getDeclaredField("price");
        price.setAccessible(true);
        price.set(car, 6000);

        assertThat(car.getPrice()).isEqualTo(6000);
    }

    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스를 생성한다.")
    void constructorWithArgs() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<Car> carClass = Car.class;
        List<Constructor<?>> constructors = Arrays.stream(carClass.getDeclaredConstructors())
                .filter(constructor -> constructor.getParameterCount() > 0)
                .toList();

        assertThat(constructors).hasSize(1);

        Car car = (Car) constructors.get(0).newInstance("S60", 5000);

        assertThat(car.getName()).isEqualTo("S60");
        assertThat(car.getPrice()).isEqualTo(5000);
    }

}
