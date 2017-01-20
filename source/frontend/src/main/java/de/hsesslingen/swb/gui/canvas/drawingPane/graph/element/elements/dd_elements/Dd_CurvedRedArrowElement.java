package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.dd_elements;

import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Dd_CurvedRedArrowElement extends Element {

    private Group view;
    private ImageView curvedRedArrow;

    public Dd_CurvedRedArrowElement(String id) {
        super(id);

        /* create a Group */
        view = new Group();

        /* Override Default-Values */
        height = 70;
        width = 70;
        nodeType = nodeType.dd_CurvedRedArrow;

        /* load the image */
        Image image = new Image("file:frontend/src/resources/components/DialogDiagramIcons/dd_CurvedRedArrow.png");
        curvedRedArrow = new ImageView();
        curvedRedArrow.setImage(image);
        curvedRedArrow.setFitWidth(width);
        curvedRedArrow.setFitHeight(height);

        /* add the components to the Group */
        view.getChildren().addAll(curvedRedArrow);

        /* add the Group to a Node and add it as children of the Element */
        setView(view);

    }

    @Override
    public void setH(double height) {
        this.height = height;
        curvedRedArrow.setFitHeight(height);
        getElementBorder().setH(height);
        updateSizeBackend();
    }

    @Override
    public void setW(double width) {
        this.width = width;
        curvedRedArrow.setFitWidth(width);
        getElementBorder().setW(width);
        updateSizeBackend();
    }

    @Override
    public double getCenterX() {
        return width / 2;
    }

    @Override
    public double getCenterY() {
        return height / 2;
    }

}
