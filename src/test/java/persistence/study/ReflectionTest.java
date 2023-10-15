package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);
    private static final Class<Car> CAR_CLAZZ = Car.class;

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        logger.debug(CAR_CLAZZ.getName());

        Field[] fields = CAR_CLAZZ.getDeclaredFields();
        Constructor<?>[] constructors = CAR_CLAZZ.getDeclaredConstructors();
        Method[] methods = CAR_CLAZZ.getDeclaredMethods();

        List<String> fieldNames = Arrays.stream(fields).map(Field::getName).collect(Collectors.toList());
        List<Class<?>> fieldTypes = Arrays.stream(fields).map(Field::getType).collect(Collectors.toList());

        assertThat(fieldNames).containsExactlyInAnyOrder("name", "price");
        assertThat(fieldTypes).containsExactlyInAnyOrder(String.class, int.class);
        assertThat(constructors[0].getParameterTypes()).isEmpty();
        assertThat(constructors[1].getParameterTypes()).containsExactlyInAnyOrder(String.class, int.class);
        assertThat(Arrays.stream(methods).map(Method::getName)).containsExactlyInAnyOrder("printView", "testGetName", "testGetPrice");
        assertThat(Arrays.stream(methods).map(Method::getParameterCount)).containsExactlyInAnyOrder(0, 0, 0);
        assertThat(Arrays.stream(methods)
                .map(Method::getReturnType)
                .map(Class::getName)).containsExactlyInAnyOrder(void.class.getName(), String.class.getName(), String.class.getName());
    }

    @Test
    @DisplayName("test로 시작하는 메소드 실행")
    void testMethodRun() throws Exception {
        Class<Car> CAR_CLAZZ = Car.class;
        Car car = CAR_CLAZZ.getConstructor().newInstance();
        Method[] allMethods = CAR_CLAZZ.getDeclaredMethods();
        List<Method> testPrefixMethods = Arrays.stream(allMethods)
                .filter(x -> x.getName().contains("test"))
                .collect(Collectors.toList());
        testPrefixMethods
                .forEach(x -> {
                    try {
                        x.invoke(car);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Test
    @DisplayName("@PrintView 애노테이션 메소드 실행")
    void testAnnotationMethodRun() throws Exception {
        Car car = CAR_CLAZZ.getConstructor(String.class, int.class)
                .newInstance("name", 1000);
        Method[] allMethods = CAR_CLAZZ.getDeclaredMethods();

        List<Method> printViewMethods = Arrays.stream(allMethods)
                .filter(x -> x.isAnnotationPresent(PrintView.class))
                .collect(Collectors.toList());

        printViewMethods.forEach(x -> {
            try {
                x.invoke(car);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    @DisplayName("private field에 값 할당")
    public void privateFieldAccess() throws Exception {

        String name = "name";
        int price = 1000;

        Car car = CAR_CLAZZ.getConstructor().newInstance();
        Field nameField = CAR_CLAZZ.getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(car, name);

        Field priceField = CAR_CLAZZ.getDeclaredField("price");
        priceField.setAccessible(true);
        priceField.set(car, price);

        assertThat(getCarFieldValue("name", String.class, car)).isEqualTo(name);
        assertThat(getCarFieldValue("price", int.class, car)).isEqualTo(price);
    }

    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스 생성")
    void constructorWithArgs() throws Exception {
        Class<Car> CAR_CLAZZ = Car.class;
        String name = "name";
        int price = 1000;

        Constructor<?> constructor = CAR_CLAZZ.getDeclaredConstructor(String.class, int.class);

        Car car = (Car) constructor.newInstance(name, price);

        assertThat(getCarFieldValue("name", String.class, car)).isEqualTo(name);
        assertThat(getCarFieldValue("price", int.class, car)).isEqualTo(price);
    }

    private <T> T getCarFieldValue(String fieldName, Class<T> fieldType, Car carInstance) throws Exception {
        Field field = CAR_CLAZZ.getDeclaredField(fieldName);
        field.setAccessible(true);
        return (T) field.get(carInstance);
    }
}