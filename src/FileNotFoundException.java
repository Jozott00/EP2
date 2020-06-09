import java.io.IOException;

public class FileNotFoundException extends Exception {

    // TODO: implement class

    private String path;

    public FileNotFoundException(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
