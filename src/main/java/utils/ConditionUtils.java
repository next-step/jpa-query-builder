package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConditionUtils {

    /**
     * 카멜케이스 형식으로 작성된 문자열을 List 형식으로 반환합니다
     */
    public static List<String> getWordsFromCamelCase(String input) {
        List<String> words = new ArrayList<>();
        String[] wordArray = input.split("(?=\\p{Upper})");
        List<String> excludedWords = Arrays.asList("find", "by", "and", "or", "all");

        //TODO: findByIdAndMethodName 같은 형식, MethodName 자체로 가져올 수 있도록 개선 필요
        for (String word : wordArray) {
            String cleanWord = word.toLowerCase();
            if (!excludedWords.contains(cleanWord)) {
                words.add(cleanWord);
            }
        }

        return words;
    }
}
