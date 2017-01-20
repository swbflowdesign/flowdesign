package de.hsesslingen.swb;

import java.io.File;
import java.util.UUID;

public class Utils {

    /**
     * This method generates a unique ID (UUID - Universally unique identifier)
     *
     * @return UUID as String
     */
    public static String generateUUID() {
        // Creating a random UUID (Universally unique identifier).
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }


    /**
     * Determine the extension of a file
     *
     * @param file File
     * @return Extension of the file
     */
    public static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return null;
    }


    /**
     * Determine the filename of a file (without extension)
     *
     * @param file File
     * @return Filename of the file
     */
    public static String getFilenameWithoutExtension(File file) {
        String fileName = file.getName();
        int lastPeriodPos = fileName.lastIndexOf('.');
        if (lastPeriodPos > 0)
            fileName = fileName.substring(0, lastPeriodPos);

        return fileName;
    }

}
