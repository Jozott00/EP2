import java.io.IOException;

public class FileFormatException extends Exception {

    // TODO: implement class

    private int line;
    private String fileName;

    public FileFormatException(int line, String fileName) {
        this.line = line;
        this.fileName = fileName;
    }

    public int getLine() {
        return line;
    }

    public String getFileName() {
        return fileName;
    }
}
