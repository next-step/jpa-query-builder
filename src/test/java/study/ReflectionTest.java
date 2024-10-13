package study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("요구 사항 1 - 클래스 정보 출력")
    void getDeclaredInfoInCarClass() {
        Class<Car> carClass = Car.class;

        // class 이름 출력
        logger.info("Car 클래스 이름 : {}", carClass.getSimpleName());

        // 선언된 필드 출력
        List<String> declaredFieldNames = Arrays.stream(carClass.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
        logger.info("선언된 필드 개수 : {}", carClass.getDeclaredFields().length);
        logger.info("선언된 필드 : {}", declaredFieldNames);


        // 메소드 출력
        List<String> declaredMethodNames = Arrays.stream(carClass.getDeclaredMethods()).map(Method::getName).collect(Collectors.toList());
        logger.info("선언된 메소드 개수 : {}", carClass.getDeclaredMethods().length);
        logger.info("선언된 메소드 이름 : {}", declaredMethodNames);


        // 생성자 출력
        logger.info("선언된 생성자 개수 : {}", carClass.getConstructors().length);
        logger.info("선언된 생성자에 정의된 파라미터 개수 : {}", Arrays.stream(carClass.getConstructors()).map(Constructor::getParameterCount).collect(Collectors.toList()));

   }

    @Test
    @DisplayName("요구 사항 1 - 클래스 정보 출력 : Check Class Name ")
    void getDeclaredInfoInCarClass_assertClassSimpleName() {
        Class<Car> carClass = Car.class;
        String carClassName = carClass.getSimpleName();
        assertThat(carClassName).isEqualTo("Car");
    }

    @Test
    @DisplayName("요구 사항 1 - 클래스 정보 출력 : Check Fields")
    void getDeclaredInfoInCarClass_assertFields() {
        Class<Car> carClass = Car.class;
        String carClassName = carClass.getSimpleName();

        List<String> declaredFieldNames = Arrays.stream(carClass.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
        assertAll(
                () -> assertThat(declaredFieldNames.contains("price")).isTrue(),
                () -> assertThat(declaredFieldNames.contains("name")).isTrue()
        );

    }

    @Test
    @DisplayName("요구 사항 1 - 클래스 정보 출력 : get Field Type")
    void getDeclaredInfoInCarClass_getFieldType() {
        Class<Car> carClass = Car.class;
        String carClassName = carClass.getSimpleName();

        List<Field> declaredFields = Arrays.stream(carClass.getDeclaredFields()).collect(Collectors.toList());
        logger.info("선언된 필드의 타입 : {}", declaredFields.stream().map(n->n.getType()).collect(Collectors.toList()));
    }

    @Test
    @DisplayName("요구 사항 1 - 클래스 정보 출력 : Check Methods")
    void getDeclaredInfoInCarClass_assertMethods() {
        Class<Car> carClass = Car.class;
        String carClassName = carClass.getSimpleName();

        List<String> declaredMethodNames = Arrays.stream(carClass.getDeclaredMethods()).map(Method::getName).collect(Collectors.toList());

        assertAll(
                () -> assertThat(carClass.getDeclaredMethods().length).isEqualTo(5),
                () -> assertThat(declaredMethodNames.contains("printView")).isTrue(),
                () -> assertThat(declaredMethodNames.contains("testGetName")).isTrue(),
                () -> assertThat(declaredMethodNames.contains("testGetPrice")).isTrue(),
                () -> assertThat(declaredMethodNames.contains("getPrice")).isTrue(),
                () -> assertThat(declaredMethodNames.contains("getName")).isTrue()
        );


    }

    @Test
    @DisplayName("요구 사항 1 - 클래스 정보 출력 : Check Constructors")
    void getDeclaredInfoInCarClass_assertConstructors() {
        Class<Car> carClass = Car.class;
        String carClassName = carClass.getSimpleName();

        assertThat(carClass.getConstructors().length).isEqualTo(2);

        // Q. 생성자에 정의된 타입 출력
        // Q. 생성자에 정의된 이름 출력
    }

   @Test
   @DisplayName("요구 사항 2 - test로 시작하는 메소드 실행")
   void testMethodRun() throws Exception {
        // Car Class에 정의된 메소드 중 'test'로 시작하는 메소드 가져오기
       Class<Car> carClass = Car.class;
       List<Method> declaredMethodsStartWithTest = Arrays.stream(carClass.getDeclaredMethods())
               .filter(method->method.getName().startsWith("test")).collect(Collectors.toList());

       // Car Object 정의
       Car testCar = getTestCar();

       // 정의된 메소드 실행
       for (Method method : declaredMethodsStartWithTest) {
           String returnValue = (String)method.invoke(testCar);
           logger.info("{} 실행 결과 = {}", method.getName(), returnValue);
       }
   }

   private Car getTestCar() throws Exception {
       Class<Car> carClass = Car.class;

       Car testCar = carClass
               .getConstructor(String.class, int.class)
               .newInstance("teslar", 4000);
       logger.info("정의한 Car 정보 : name = {}, price = {}", testCar.getName(), testCar.getPrice());

       assertAll(
               () -> assertThat(testCar.getName()).isEqualTo("teslar"),
               () -> assertThat(testCar.getPrice()).isEqualTo(4000)
       );

       return  testCar;
   }

   @Test
   @DisplayName("요구 사항 3 - @PrintView 애노테이션 메소드 실행")
   void testAnnotationMethodRun() throws Exception {
        // Car Class에 정의된 메소드 중 PrintView Class로 정의된 Annotation을 가진 메소드 가져오기
       Class<Car> carClass = Car.class;
       List<Method> declaredMethodAnnotatedWithPrintView = Arrays.stream(carClass.getDeclaredMethods())
               .filter(method -> method.isAnnotationPresent(PrintView.class)).collect(Collectors.toList());

       assertThat(declaredMethodAnnotatedWithPrintView.size()).isEqualTo(1);

       // Car Object 정의
       Car testCar = getTestCar();

       // 정의된 메소드 실행
       for (Method method : declaredMethodAnnotatedWithPrintView) {
           method.invoke(testCar);
       }
   }

   @Test
   @DisplayName("요구 사항 4 - private field에 값 할당")
   void privateFieldAccess() throws Exception {
       Class<Car> carClass = Car.class;
       Car testCar = getTestCar();

       Field priceField = carClass.getDeclaredField("price");
       Field nameField = carClass.getDeclaredField("name");

       priceField.setAccessible(true);
       nameField.setAccessible(true);

       priceField.set(testCar, 4200);
       nameField.set(testCar, "소나타");

       logger.info("정의한 Car 정보 : name = {}, price = {}", testCar.getName(), testCar.getPrice());

       assertAll(
               () -> assertThat(testCar.getPrice()).isEqualTo(4200),
               () -> assertThat(testCar.getName()).isEqualTo("소나타")
       );
   }

   @Test
   @DisplayName("요구 사항 5 - 인자를 가진 생성자의 인스턴스 생성")
   void constructorWithArgs() throws Exception {
        Class<Car> carClass = Car.class;

        List<Constructor> declaredConstructorWithParameter = Arrays.stream(carClass.getDeclaredConstructors()).filter(constructor-> constructor.getParameters().length > 0).collect(Collectors.toList());
        logger.info("선언된 생성자 개수: {}", carClass.getDeclaredConstructors().length);

        assertThat(declaredConstructorWithParameter.size()).isEqualTo(1);
        Constructor constructorWithNameAndPrice = declaredConstructorWithParameter.get(0);
        Car newCar = (Car) constructorWithNameAndPrice.newInstance("teslar", 4000);

        assertAll(
                () -> assertThat(newCar.getName()).isEqualTo("teslar"),
                () -> assertThat(newCar.getPrice()).isEqualTo(4000)
        );
   }
}
