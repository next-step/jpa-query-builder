package persistence.study;

import org.junit.jupiter.api.BeforeEach;
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
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);
    private Class<Car> carClass;

    @BeforeEach
    void setUp() {
        carClass = Car.class;
    }

    @Test
    @DisplayName("요구사항1. Car 클래스의 모든 필드, 생성자, 메소드 정보를 출력한다.")
    void showClass() {
        List<String> fields = convertArrayToListWithMapping(carClass.getDeclaredFields(), Field::getName);
        List<String> constructor = convertArrayToListWithMapping(carClass.getDeclaredConstructors(), Constructor::getName);
        List<String> methods = convertArrayToListWithMapping(carClass.getDeclaredMethods(), Method::getName);

        assertThat(fields).containsOnly("name", "price");
        assertThat(constructor).containsOnly("persistence.study.Car", "persistence.study.Car");
        assertThat(methods).containsOnly("testGetPrice", "printView", "testGetName");
    }

    private <T, R> List<R> convertArrayToListWithMapping(T[] array, Function<T, R> map) {
        return Arrays.stream(array).map(map).collect(Collectors.toList());
    }

    @Test
    @DisplayName("test로 시작하는 메소드 실행")
    void testMethodRun() throws InvocationTargetException, IllegalAccessException {
        carClass = Car.class;
        Car car = new Car();
        Method[] methods = carClass.getMethods();
        for (Method method : methods) {
            isStartWithTest(car, method);
        }
    }
    private void isStartWithTest(final Car car, final Method method) throws IllegalAccessException, InvocationTargetException {
        if(method.getName().startsWith("test")) {
            String invoke = (String) method.invoke(car);
            assertThat(invoke).startsWith("test : ");
        }
    }

}