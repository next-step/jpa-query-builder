package persistence.study;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ListUtils {

    public static <T> List<T> filter(List<T> allMethods, Predicate<T> predicate) {
        return allMethods.stream().filter(
                predicate
        ).collect(Collectors.toList());
    }
}
