package de.hsesslingen.swb;

import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


public class FileAPI {

    /**
     * Import a image to the project
     *
     * @param imagePath Path of the image file
     * @return New name of the image
     */
    public static String importImage(String imagePath) {

        /* Check file */
        File sourceFile = new File(imagePath);
        if (!sourceFile.exists() || !sourceFile.isFile()) {
            return null;
        }

        /* Compute sourceFile */
        String newName = Utils.generateUUID() + "." + Utils.getFileExtension(sourceFile);
        File destFile = new File(DataStorage.projectDirectory + File.separator + "resources" + File.separator + "images" + File.separator + newName);

        /* Copy image */
        try {
            Files.copy(sourceFile.toPath(), destFile.toPath());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }

        return newName;

    }


    /**
     * Get a Image from the resource/images folder
     *
     * @param imageName Name of the image
     * @return Image
     */
    public static Image getImageFromResources(String imageName) {

        /* Check file */
        File imageFile = new File(DataStorage.projectDirectory + File.separator + "resources" + File.separator + "images" + File.separator + imageName);
        if (!imageFile.exists() || !imageFile.isFile()) {
            return null;
        }

        /* Get image */
        Image image = new Image(imageFile.toURI().toString());

        return image;

    }


    /**
     * Get all images (their names) inside the resources/images folder
     *
     * @return List of image names
     */
    public static List<String> getAllImagesFromResources() {

        /* Get resource/images folder */
        File imageFolder = new File(DataStorage.projectDirectory + File.separator + "resources" + File.separator + "images");


        /* Check if images are available */
        if (imageFolder.listFiles() == null)
            return null;

        /* Fetch all images in the folder */
        List<String> images = new ArrayList<String>();
        for (File imageFile : imageFolder.listFiles()) {
            if (!imageFile.isDirectory()) {
                images.add(imageFile.getName());
            }
        }

        /* If images available return the list */
        if (images.size() > 0) {
            return images;
        } else {
            return null;
        }

    }

}