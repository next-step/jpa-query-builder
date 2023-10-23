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

    public CustomStringBuilder appendWithoutSpace(String str) {
        sb.append(str);
        return this;
    }

    public String toString() {
        if (sb.lastIndexOf(" ") == sb.length() - 1) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    public String toStringWithoutSpace() {
        return sb.toString();
    }

}
