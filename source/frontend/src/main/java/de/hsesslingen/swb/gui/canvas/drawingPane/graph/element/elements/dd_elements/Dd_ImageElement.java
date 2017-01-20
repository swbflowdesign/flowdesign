package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.dd_elements;

import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.Image;
import javafx.scene.Group;

public class Dd_ImageElement extends Element {

    private Group view;
    private Image imageNode;

    public Dd_ImageElement(String id, String imageName) {
        super(id);

        /* Override default values */
        nodeType = nodeType.dd_Image;
        image = imageName;

        /* create a Group */
        view = new Group();

        /* create necessary components */
        imageNode = new Image(imageName);

        /* get width and height from unit */
        width = imageNode.getW();
        height = imageNode.getH();

        /* add the components to the Group */
        view.getChildren().addAll(imageNode.getImageView());

        /* add the Group to a Node and add it as children of the Element */
        setView(view);
    }

    @Override
    public void setH(double height) {
        this.height = height;
        imageNode.setH(height);
        getElementBorder().setH(height);
        updateSizeBackend();
    }

    @Override
    public void setW(double width) {
        this.width = width;
        imageNode.setW(width);
        getElementBorder().setW(width);
        updateSizeBackend();
    }


    @Override
    public String getImage() {
        return image;
    }

    @Override
    public void setImage(String image) {
        this.imageNode.setImage(image);
        updateImageBackend();
    }


}
