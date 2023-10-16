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
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);
    private static final Class<Car> carClass = Car.class;

    @Test
    @DisplayName("요구사항 1-1. 클래스의 모든 필드 정보 출력")
    void showClassField() {
        List<String> fieldNames = fromArrayToList(carClass.getDeclaredFields(), Field::toString);
        print("필드", fieldNames);
    }

    @Test
    @DisplayName("요구사항 1-1. 클래스의 모든 생성자 정보 출력")
    void showClassConstructors() {
        List<String> constructorNames = fromArrayToList(carClass.getDeclaredConstructors(), Constructor::toString);
        print("생성자", constructorNames);
    }

    @Test
    @DisplayName("요구사항 1-1. 클래스의 모든 메소드 정보 출력")
    void showClassMethods() {
        List<String> methodNames = fromArrayToList(carClass.getDeclaredMethods(), Method::toString);
        print("메소드", methodNames);
    }

    // 배열을 list 로 바꿔주는 메소드
    private <T, G> List<G> fromArrayToList(T[] array, Function<T, G> toString) {
        return Arrays.stream(array)
                .map(toString)
                .collect(Collectors.toList());
    }

    private void print(String name, List<String> printItems) {
        printItems.forEach(item -> logger.debug("{}의 {}번째 요소 : {}", name, printItems.indexOf(item), item));
    }
}
