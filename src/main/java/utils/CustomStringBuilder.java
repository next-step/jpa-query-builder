package utils;

public class CustomStringBuilder {

    private final StringBuilder sb;

    public CustomStringBuilder() {
        this.sb = new StringBuilder();
    }

    public CustomStringBuilder append(String str) {
        sb.append(str);

        if (str.lastIndexOf(" ") != str.length() - 1) {
            sb.append(" ");
        }

        return this;
    }

    // TODO 공백 핸들링 하는 부분 좀 더 깔끔하게 구현 해보자.
    public String toString() {
        if (sb.lastIndexOf(" ") == sb.length() - 1) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

}
