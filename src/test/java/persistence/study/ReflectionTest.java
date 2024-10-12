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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;

        assertAll(
                () -> assertThat(carClass.getName()).isEqualTo("persistence.study.Car"),
                () -> assertThat(carClass.getSimpleName()).isEqualTo("Car"),
                () -> assertThat(carClass.getPackage().getName()).isEqualTo("persistence.study")
        );
    }

    @Test
    @DisplayName("test로 시작하는 메소드 실행")
    void startTestMethodsExecute() throws Exception {
        Car tesla = new Car("Tesla", 10_000);
        Class<? extends Car> carClass = tesla.getClass();

        List<Method> testStartMethods = Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .toList();

        for (Method testStartMethod : testStartMethods) {
            Object invoke = testStartMethod.invoke(tesla);
            System.out.println(invoke);
        }
    }

    @Test
    @DisplayName("PrintView 어노테이션 메소드 실행")
    void printViewAnnotationMethodsExecute() throws Exception {
        Car tesla = new Car("Tesla", 10_000);
        Class<? extends Car> carClass = tesla.getClass();

        List<Method> printViewMethods = Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .toList();

        for (Method printViewMethod : printViewMethods) {
            printViewMethod.invoke(tesla);
        }
    }

    @Test
    @DisplayName("private field에 값 할당하기")
    void assignValueToPrivateField() throws Exception {
        Car tesla = new Car("Tesla", 10_000);
        Class<? extends Car> carClass = tesla.getClass();

        Field nameField = carClass.getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(tesla, "Volvo");

        Field priceField = carClass.getDeclaredField("price");
        priceField.setAccessible(true);
        priceField.set(tesla, 20_000);

        assertAll(
                () -> assertThat(tesla.testGetName()).isEqualTo("test : Volvo"),
                () -> assertThat(tesla.testGetPrice()).isEqualTo("test : 20000")
        );
    }

    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스 생성하기")
    void createInstanceWithConstructor() throws Exception {
        Class<Car> carClass = Car.class;
        Constructor<?> argsConstructor = Arrays.stream(carClass.getDeclaredConstructors())
                .filter(constructor -> constructor.getParameterCount() > 0)
                .findFirst()
                .orElseThrow();

        Car volvoCar = (Car) argsConstructor.newInstance("Volvo", 20_000);

        assertAll(
                () -> assertThat(volvoCar.testGetName()).isEqualTo("test : Volvo"),
                () -> assertThat(volvoCar.testGetPrice()).isEqualTo("test : 20000")
        );
    }
}
