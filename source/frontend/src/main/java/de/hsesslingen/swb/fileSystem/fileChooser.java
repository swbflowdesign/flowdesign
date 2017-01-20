package de.hsesslingen.swb.fileSystem;

import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class fileChooser {

    /**
     * Opens a FileChooser to to select a single file
     *
     * @param extensions String array containing the allowed file extensions.
     *                   The available extensions can be found in the function getExtensionFilter.
     * @return FileChooser
     */
    public static File showOpenDialog(String[] extensions) {

        /* New fileChooser */
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Datei öffnen");

        /* Set initial directory to home */
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );

        /* Add extension filters */
        if (extensions != null) {
            for (String extension : extensions) {
                FileChooser.ExtensionFilter extFilter = getExtensionFilter(extension);
                if (extFilter != null)
                    fileChooser.getExtensionFilters().add(extFilter);
            }
        }

        /* Open fileChooser */
        return fileChooser.showOpenDialog(null);

    }

    /**
     * Opens a FileChooser to to select multiple files
     *
     * @param extensions String array containing the allowed file extensions.
     *                   The available extensions can be found in the function getExtensionFilter.
     * @return FileChooser
     */
    public static List<File> showOpenMultipleDialog(String[] extensions) {

        /* New fileChooser */
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Datei öffnen");

        /* Set initial directory to home */
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );

        /* Add extension filters */
        if (extensions != null) {
            for (String extension : extensions) {
                FileChooser.ExtensionFilter extFilter = getExtensionFilter(extension);
                if (extFilter != null)
                    fileChooser.getExtensionFilters().add(extFilter);
            }
        }

        /* Open fileChooser */
        return fileChooser.showOpenMultipleDialog(null);

    }

    private static FileChooser.ExtensionFilter getExtensionFilter(String extension) {

        if (extension.equals("png") || extension.equals("PNG"))
            return new FileChooser.ExtensionFilter("PNG (*.png)", "*.png");

        if (extension.equals("jpg") || extension.equals("JPG"))
            return new FileChooser.ExtensionFilter("JPG (*.jpg)", "*.jpg");

        if (extension.equals("flowproject") || extension.equals("FLOWPROJECT"))
            return new FileChooser.ExtensionFilter("Flow Desiner (*.flowproject)", "*.flowproject");

        if (extension.equals("xml") || extension.equals("XML"))
            return new FileChooser.ExtensionFilter("XML (*.xml)", "*.xml");

        if (extension.equals("txt") || extension.equals("TXT"))
            return new FileChooser.ExtensionFilter("Text (*.txt)", "*.txt");

        if (extension.equals("Bilder"))
            return new FileChooser.ExtensionFilter("Bilder", "*.png", "*.jpg", "*.gif");

        return null;
    }

}
