package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents;

import de.hsesslingen.swb.BackendAPI;
import javafx.scene.image.ImageView;

public class Image {

    ImageView imageView;
    double height;
    double width;

    public Image(String imageName) {

        /* Create new element */
        javafx.scene.image.Image imageFile = BackendAPI.file().getImageFromResources(imageName);
        imageView = new ImageView(imageFile);
        imageView.setFitHeight(imageFile.getHeight());
        imageView.setFitWidth(imageFile.getWidth());

        /* Set height and width */
        height = imageFile.getHeight();
        width = imageFile.getWidth();
    }

    /*Getter*/

    /**
     * get the view
     *
     * @return view
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * set the view
     *
     * @param imageView
     */
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    /**
     * get the height of the unit
     *
     * @return height of unit
     */
    public double getH() {
        return height;
    }


    /*Setter*/

    /**
     * set the height and display it on screen
     *
     * @param height
     */
    public void setH(double height) {
        this.height = height;
        this.imageView.setFitHeight(height);
    }

    /**
     * get the width of the unit
     *
     * @return width of unit
     */
    public double getW() {
        return width;
    }

    /**
     * set the width and display it on screen
     *
     * @param width
     */
    public void setW(double width) {
        this.width = width;
        this.imageView.setFitWidth(width);
    }

    /**
     * set the image
     *
     * @param imageName
     */
    public void setImage(String imageName) {
        this.imageView.setImage(BackendAPI.file().getImageFromResources(imageName));
    }

}
