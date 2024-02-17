package common;

public class CamelCaseToSnakeCaseConverter {

    private CamelCaseToSnakeCaseConverter() {
    }

    public static String convertToSnakeCase(final String input) {
        // 입력이 null이거나 비어있는 경우, 그대로 반환
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder builder = new StringBuilder();
        char[] chars = input.toCharArray();

        // 첫 번째 문자는 대문자여도 그대로 추가 (대문자인 경우 소문자로 변경)
        builder.append(Character.toLowerCase(chars[0]));

        // 두 번째 문자부터 마지막 문자까지 검사
        for (int i = 1; i < chars.length; i++) {
            char currentChar = chars[i];

            // 현재 문자가 대문자인 경우, 앞에 "_"를 추가하고 소문자로 변경
            if (Character.isUpperCase(currentChar)) {
                builder.append('_');
                builder.append(Character.toLowerCase(currentChar));
            } else {
                // 그 외의 경우, 문자를 그대로 추가
                builder.append(currentChar);
            }
        }

        return builder.toString();
    }
}
