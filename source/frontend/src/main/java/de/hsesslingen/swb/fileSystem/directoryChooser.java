package de.hsesslingen.swb.fileSystem;

import javafx.stage.DirectoryChooser;

import java.io.File;

public class directoryChooser {

    /**
     * Opens a DirectoryChooser
     *
     * @return DirectoryChooser OpenFileDialog to select a directory
     */
    public static File showDirectoryDialog() {

        /* New directoryChooser */
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Verzeichnis wählen");

        /* Set initial directory to home */
        directoryChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );

        /* Open directoryChooser */
        return directoryChooser.showDialog(null);

    }

}
