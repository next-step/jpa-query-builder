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
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class ReflectionTest {
    private static final Class<Car> carClass = Car.class;

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        // 패키지+클래스 이름
        assertThat(carClass.getName()).isEqualTo("persistence.study.Car");

        // 모든 필드 목록
        List<String> fields = Arrays.stream(carClass.getDeclaredFields())
                .map(Field::toString)
                .collect(Collectors.toList());
        assertThat(fields).contains(
                "private java.lang.String persistence.study.Car.name",
                "private int persistence.study.Car.price"
        );

        // 모든 생성자 반환
        List<String> constructors = Arrays.stream(carClass.getDeclaredConstructors())
                .map(Constructor::toString)
                .collect(Collectors.toList());
        assertThat(constructors).contains(
                "public persistence.study.Car()",
                "public persistence.study.Car(java.lang.String,int)"
        );

        // 모든 메서드 반환
        List<String> methods = Arrays.stream(carClass.getDeclaredMethods())
                .map(Method::toString)
                .collect(Collectors.toList());
        assertThat(methods).contains(
                "public java.lang.String persistence.study.Car.getName()",
                "public void persistence.study.Car.printView()",
                "public int persistence.study.Car.getPrice()",
                "public java.lang.String persistence.study.Car.testGetName()",
                "public java.lang.String persistence.study.Car.testGetPrice()"
        );
    }

    @Test
    @DisplayName("test로 시작하는 메소드 실행")
    void testMethodRun() throws InvocationTargetException, IllegalAccessException {
        Car mockCar = mock(Car.class);

        List<Method> startWithTestMethods = Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .collect(Collectors.toList());
        for (final Method method : startWithTestMethods) {
            method.invoke(mockCar);
        }

        assertAll(
                () -> verify(mockCar, times(1)).testGetName(),
                () -> verify(mockCar, times(1)).testGetPrice()
        );
    }

    @Test
    @DisplayName("@PrintView 애노테이션 메소드 실행")
    void testAnnotationMethodRun() throws InvocationTargetException, IllegalAccessException {
        Car mockCar = mock(Car.class);

        List<Method> annotationMethods = Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .collect(Collectors.toList());
        for (final Method method : annotationMethods) {
            method.invoke(mockCar);
        }

        verify(mockCar, times(1)).printView();
    }

    @Test
    @DisplayName("private field에 값 할당")
    void privateFieldAccess() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        final Class<Car> carClass = Car.class;
        final Car car = carClass.getConstructor().newInstance();
        final String name = "Sonata";
        final int price = 1000;

        Field nameField = carClass.getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(car, name);
        Field priceField = carClass.getDeclaredField("price");
        priceField.setAccessible(true);
        priceField.set(car, price);

        assertAll(
                () -> assertThat(car.getName()).isEqualTo(name),
                () -> assertThat(car.getPrice()).isEqualTo(price)
        );
    }

    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스 생성")
    void constructorWithArgs() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final String name = "Sonata";
        final int price = 1000;

        final Car car = carClass.getConstructor(String.class, int.class).newInstance(name, price);

        assertAll(
                () -> assertThat(car).isInstanceOf(Car.class),
                () -> assertThat(car.getName()).isEqualTo(name),
                () -> assertThat(car.getPrice()).isEqualTo(price)
        );
    }
}
