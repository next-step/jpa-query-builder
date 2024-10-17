package common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SqlParser {
    public static String parse(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }
}
