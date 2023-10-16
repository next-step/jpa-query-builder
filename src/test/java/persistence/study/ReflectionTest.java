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
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);
    private static final Class<Car> carClass = Car.class;

    @Test
    @DisplayName("요구사항 1-1. 클래스의 모든 필드 정보 출력")
    void showClassField() {
        List<String> fieldNames = fromArrayToList(carClass.getDeclaredFields(), Field::getName);
        print("필드", fieldNames);
    }

    @Test
    @DisplayName("요구사항 1-1. 클래스의 모든 생성자 정보 출력")
    void showClassConstructors() {
        List<String> constructorNames = fromArrayToList(carClass.getDeclaredConstructors(), Constructor::getName);
        print("생성자", constructorNames);
    }

    @Test
    @DisplayName("요구사항 1-1. 클래스의 모든 메소드 정보 출력")
    void showClassMethods() {
        List<String> methodNames = fromArrayToList(carClass.getDeclaredMethods(), Method::getName);
        print("메소드", methodNames);
    }

    @Test
    @DisplayName("요구사항 2. test로 시작하는 메소드 실행")
    void testMethodRun() {
        //given
        Method[] declaredMethods = carClass.getDeclaredMethods();
        //when
        List<Object> results = Arrays.stream(declaredMethods)
                .filter(method -> method.getName().startsWith("test"))
                .map(method -> {
                    try {
                        return method.invoke(new Car());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
        //then
        results.forEach(result -> {
            assertThat(result.toString().startsWith("test : ")).isTrue();
        });
    }

    @Test
    @DisplayName("요구사항 3 - @PrintView 애노테이션 메소드 실행")
    void testAnnotationMethodRun() {
        //given when
        Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                        .forEach(method -> {
                            try {
                                method.invoke(new Car());
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }
                        });
    }

    @Test
    @DisplayName("요구사항 4 - private field에 값 할당")
    void privateFieldAccess() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        Car car = carClass.getDeclaredConstructor().newInstance();
        String carName = "붕붕이";
        int carPrice = 30000;
        Field name = car.getClass().getDeclaredField("name");
        Field price = car.getClass().getDeclaredField("price");
        name.setAccessible(true);
        name.set(car,carName);
        price.setAccessible(true);
        price.setInt(car, carPrice);
        assertThat(car.getName()).isEqualTo(carName);
        assertThat(car.getPrice()).isEqualTo(carPrice);
    }

    // 배열을 list 로 바꿔주는 메소드
    private <T, G> List<G> fromArrayToList(T[] array, Function<T, G> toListFunction) {
        return Arrays.stream(array)
                .map(toListFunction)
                .collect(Collectors.toList());
    }

    private void print(String name, List<String> printItems) {
        printItems.forEach(item -> logger.debug("{}의 {}번째 요소 : {}", name, printItems.indexOf(item), item));
    }
}
