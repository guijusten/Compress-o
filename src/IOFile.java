import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IOFile {
    public static File getFileFromDialog() throws FileNotFoundException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int result = fileChooser.showOpenDialog(null);

        if (result != JFileChooser.APPROVE_OPTION) {
            throw new FileNotFoundException();
        }

        return fileChooser.getSelectedFile();
    }

    public static String getStringFromFile() throws FileNotFoundException {
        File inputFile;
        String data = "";

        inputFile = IOFile.getFileFromDialog();
        System.out.printf("Lendo arquivo %s...\n\n", inputFile.getName());
        try {
            Scanner reader = new Scanner(inputFile);
            if (reader.hasNextLine()) {
                data = reader.nextLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }
}
