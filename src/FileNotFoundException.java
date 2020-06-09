import java.io.IOException;

public class FileNotFoundException extends IOException {

    // TODO: implement class
    public FileNotFoundException(String Path) {
        super("Error: File \"" + Path + "\" not found.");
    }
}
