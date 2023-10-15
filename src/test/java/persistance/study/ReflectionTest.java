package persistance.study;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ReflectionTest {

    Class<Car> carClass = Car.class;

    @Test
    void 클래스명를_가져온다() {
        String actual = carClass.getName();
        assertThat(actual).isEqualTo("persistance.study.Car");
    }

    @Test
    void 필드명을_가져온다() {
        List<String> actual = Arrays.stream(carClass.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toList());
        assertThat(actual).containsExactly("name", "price");
    }

    @Test
    void 생성자정보를_가져온다() {
        Constructor<?>[] constructors = carClass.getDeclaredConstructors();

        List<String> actualNames = Arrays.stream(constructors)
                .map(Constructor::getName)
                .collect(Collectors.toList());
        List<Integer> actualTypes = Arrays.stream(constructors)
                .map(Constructor::getModifiers)
                .collect(Collectors.toList());

        assertThat(actualNames).containsExactly("persistance.study.Car", "persistance.study.Car");
        assertThat(actualTypes).containsExactly(1, 1);
    }

    @Test
    void 메소드정보를_가져온다() {
        Method[] methods = carClass.getDeclaredMethods();

        List<String> actualNames = Arrays.stream(methods)
                .map(Method::getName)
                .collect(Collectors.toList());
        List<Integer> actualModifier = Arrays.stream(methods)
                .map(Method::getModifiers)
                .collect(Collectors.toList());

        assertThat(actualNames).containsExactlyInAnyOrder("printView", "testGetName", "testGetPrice");
        assertThat(actualModifier).containsExactlyInAnyOrder(1, 1, 1);
    }

    @Test
    void test로_시작하는_메소드를_실행한다() {
        Car car = new Car("자동차", 1000);
        Stream<Method> actualMethods = Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"));
        List<Object> actual = actualMethods.map(method -> {
            try {
                return method.invoke(car);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

        assertThat(actual).containsExactlyInAnyOrder("test : 자동차", "test : 1000");
    }

    @Test
    void printView어노테이션을_실행한다() {
        Car car = new Car("자동차", 1000);
        List<Method> actualMethods = Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .collect(Collectors.toList());

        actualMethods.forEach(method -> {
            try {
                method.invoke(car);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void private_필드에_값을_할당한다() throws NoSuchFieldException, IllegalAccessException {
        Car car = new Car();
        Field nameField = carClass.getDeclaredField("name");
        nameField.setAccessible(true);

        nameField.set(car, "쏘나타");
        Object actual = nameField.get(car);
        assertThat(actual).isEqualTo("쏘나타");
    }

    @Test
    void 인자를_가진_생성자의_인스터스를_생성한다() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> actualConstructor = Arrays.stream(carClass.getDeclaredConstructors())
                .filter(constructor -> constructor.getParameterCount() > 0)
                .findFirst()
                .get();
        Car actual = (Car) actualConstructor.newInstance("쏘나타", 1000);
        assertThat(actual).isEqualTo(new Car("쏘나타", 1000));
    }
}
