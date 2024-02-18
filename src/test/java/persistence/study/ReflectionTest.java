package persistence.study;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

public class ReflectionTest {
  private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);
  private final Class<Car> carClass = Car.class;

  @Test
  @DisplayName("Car 객체 정보 가져오기")
  void showClass() {
    String carClassName = carClass.getName();
    logger.debug(carClassName);
  }

  @Test
  @DisplayName("Car 클래스에서 test로 시작하는 메소드만 실행하기")
  void toMethodRun() throws Exception {
    Car car = carClass.newInstance();

    Method[] declaredMethods = carClass.getDeclaredMethods();
    List<Object> result = Arrays.stream(declaredMethods)
      .filter(declaredMethod -> {
        String declaredMethodName = declaredMethod.getName();
        return declaredMethodName.startsWith("test");
      })
      .map(declaredTestMethod -> invokeMethod(declaredTestMethod, car))
      .collect(Collectors.toList());

    assertThat(result).hasSize(2);
    assertThat(result).contains("test : null", "test : 0");
  }
  
  private Object invokeMethod(Method declaredTestMethod, Car car) {
    try {
      return declaredTestMethod.invoke(car);
    } catch (IllegalAccessException | InvocationTargetException e) {
      fail();
    }
    return null;
  }
}