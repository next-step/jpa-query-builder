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

public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("요구 사항 1 - 클래스 정보 출력")
    void getDeclaredInfoInCarClass() {
        Class<Car> carClass = Car.class;
        String carClassName = carClass.getSimpleName();

        // class 이름 출력
        logger.info("Car 클래스 이름 : {}", carClass.getSimpleName());
        assertThat(carClassName).isEqualTo("Car");

        // 선언된 필드 출력
        List<String> declaredFieldNames = Arrays.stream(carClass.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
        logger.info("선언된 필드 개수 : {}", carClass.getDeclaredFields().length);
        logger.info("선언된 필드 : {}", declaredFieldNames);
        assertThat(declaredFieldNames.contains("price")).isTrue();
        assertThat(declaredFieldNames.contains("name")).isTrue();

        // 메소드 출력
        List<String> declaredMethodNames = Arrays.stream(carClass.getMethods()).map(Method::getName).collect(Collectors.toList());
        logger.info("선언된 메소드 개수 : {}", carClass.getMethods().length);
        logger.info("선언된 메소드 이름 : {}", declaredMethodNames);
        assertThat(declaredMethodNames.contains("printView")).isTrue();
        assertThat(declaredMethodNames.contains("testGetName")).isTrue();
        assertThat(declaredMethodNames.contains("testGetPrice")).isTrue();

        // 생성자 출력
        logger.info("선언된 생성자 개수 : {}", carClass.getConstructors().length);
        logger.info("선언된 생성자에 정의된 파라미터 개수 : {}", Arrays.stream(carClass.getConstructors()).map(Constructor::getParameterCount).collect(Collectors.toList()));
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
       assertThat(testCar.getName()).isEqualTo("teslar");
       assertThat(testCar.getPrice()).isEqualTo(4000);
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
       assertThat(testCar.getPrice()).isEqualTo(4200);
       assertThat(testCar.getName()).isEqualTo("소나타");
   }
}
