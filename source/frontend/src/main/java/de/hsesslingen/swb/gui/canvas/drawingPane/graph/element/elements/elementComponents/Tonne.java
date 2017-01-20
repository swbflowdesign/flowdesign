package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tonne {

    ImageView view;
    double width;
    double height;

    public Tonne() {

        /*Set width and height*/
        width = 40;
        height = 40;

        /*Load the image*/
        Image image = new Image("file:frontend/src/resources/components/FlowDesignIcons/Tonne.png");
        view = new ImageView();
        view.setImage(image);
        view.setFitWidth(width);
        view.setFitHeight(height);

    }

    /*Getter*/
    public ImageView getView() {
        return view;
    }

    /*Setter*/
    public void setView(ImageView view) {
        this.view = view;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }


}
