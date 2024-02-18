package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ReflectionTest {

    private final Class<Car> carClass = Car.class;

    @Test
    @DisplayName("Car 클래스의 필드명을 조회한다.")
    void getCarFields() {
        // given
        Field[] fields = carClass.getDeclaredFields();

        // when
        List<String> result = Arrays.stream(fields)
                .map(Field::getName)
                .collect(Collectors.toList());

        // then
        assertThat(result).containsExactlyInAnyOrder("name", "price");
    }

    @Test
    @DisplayName("Car 클래스의 생성자 파라미터 개수를 조회한다.")
    void getCarConstructorParameters() {
        // given
        Constructor<?>[] constructors = carClass.getDeclaredConstructors();

        // when
        List<Integer> result = Arrays.stream(constructors)
                .map(Constructor::getParameterCount)
                .collect(Collectors.toList());

        // then
        assertThat(result).containsExactlyInAnyOrder(0, 2);
    }

    @Test
    @DisplayName("Car 클래스의 메소드 이름을 조회한다.")
    void getCarMethods() {
        // given
        Method[] methods = carClass.getDeclaredMethods();

        // when
        List<String> result = Arrays.stream(methods)
                .map(Method::getName)
                .collect(Collectors.toList());

        // then
        assertThat(result).containsExactlyInAnyOrder("printView", "testGetPrice", "testGetName", "equals", "hashCode");
    }

    @Test
    @DisplayName("Car 클래스의 test 로 시작하는 메서드를 실행한다.")
    void runTestMethod() {
        // given
        List<Method> startWithTestMethods = Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .collect(Collectors.toList());

        // when
        List<Object> result = startWithTestMethods.stream()
                .map(this::invokeMethod)
                .collect(Collectors.toList());

        // then
        assertThat(result).containsExactlyInAnyOrder("test : name", "test : 10");
    }

    @Test
    @DisplayName("Car 클래스의 PrintView 어노테이션이 붙은 메서드를 실행한다.")
    void runAnnotationMethodRun() {
        // given
        List<Method> printViewAnnotationMethod = Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .collect(Collectors.toList());

        // when & then
        for (Method method : printViewAnnotationMethod) {
            invokeMethod(method);
        }
    }

    @Test
    @DisplayName("Car 클래스에 필드에 값을 할당한다.")
    void privateFieldAccess() {
        // given
        Object car = createNewInstance("", 0);

        String newName = "name";
        int newPrice = 10;

        // then
        for (Field field : car.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            final Object value = field.getType() == String.class ? newName : newPrice;
            try {
                field.set(car, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        // then
        assertThat(car).isEqualTo(new Car(newName, newPrice));
    }

    @Test
    @DisplayName("Car 클래스에서 인자를 가진 인스턴스를 생성한다.")
    void createConstructWithParameter() {
        // given
        String name = "name";
        int price = 10;

        // when
        Object car = createNewInstance(name, price);

        // then
        assertThat(car).isEqualTo(new Car(name, price));
    }


    private Object createNewInstance(String name, int price) {
        Class<Car> carClass = Car.class;
        try {
            return carClass.getDeclaredConstructor(String.class, int.class).newInstance(name, price);
        } catch (IllegalAccessException | InvocationTargetException |
                 InstantiationException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private Object invokeMethod(Method method) {
        try {
            return method.invoke(createNewInstance("name", 10));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
