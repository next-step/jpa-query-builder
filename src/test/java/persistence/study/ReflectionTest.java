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
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    private Class<Car> carClass = Car.class;;

    @Test
    @DisplayName("Car 클래스의 필드이름을 가져온다.")
    void showClass() {
        logger.debug(carClass.getName());

        // 필드
        List<String> result = Arrays.stream(carClass.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toList());

        assertThat(result).containsExactlyInAnyOrder("name", "price");
    }

    @Test
    @DisplayName("Car class 생성자 정보를 반환한다.")
    void showConstructor() {
        List<Integer> result = Arrays.stream(carClass.getDeclaredConstructors())
                .map(Constructor::getParameterCount)
                .collect(Collectors.toList());

        assertThat(result).containsExactlyInAnyOrder(0, 2);
    }

    @Test
    @DisplayName("Car class 메소드 정보를 반환한다.")
    void showMethod() {
        List<String> result = Arrays.stream(carClass.getDeclaredMethods())
                .map(Method::getName)
                .collect(Collectors.toList());

        assertThat(result).containsExactlyInAnyOrder("printView", "testGetPrice", "testGetName");
    }

    @Test
    void runMethod() {
        List<Object> result = Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .map(method -> {
                    try {
                        return method.invoke(createNewInstance());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        assertThat(result).containsExactlyInAnyOrder("test : 123", "test : nextstep");
    }

    private Object createNewInstance() {
        Class<Car> carClass = Car.class;
        try {
            return carClass.getDeclaredConstructor(String.class, int.class).newInstance("nextstep", 123);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void runPrintView() {
        Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .forEach(method -> {
                    logger.debug("메소드 이름 : {}", method.getName());
                    try {
                        method.invoke(createNewInstance());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Test
    @DisplayName("Car 클래스에 필드에 값을 할당한다.")
    void seField() {
        Object carInstance = createNewInstance();

        Arrays.stream(carInstance.getClass().getDeclaredFields())
                .forEach(field -> {
                    field.setAccessible(true);

                    final Object value = field.getType() == String.class ? "123" : 123;

                    try {
                        field.set(carInstance, value);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });

        List<Object> result = Arrays.stream(carInstance.getClass().getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .map(method -> {
                    try {
                        return method.invoke(carInstance);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        assertThat(result).containsExactlyInAnyOrder("test : 123", "test : 123");
    }

    @Test
    @DisplayName("Car 클래스에서 인자를 가진 인스턴스를 생성한다.")
    void createConstructWithParameter() {
        Object carInstance = createNewInstance();

        List<Object> result = Arrays.stream(carInstance.getClass().getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .map(method -> {
                    try {
                        return method.invoke(createNewInstance());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        assertThat(result).containsExactlyInAnyOrder("test : 123", "test : nextstep");
    }

}
