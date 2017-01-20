package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.sud_elements;

import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sud_DatabaseElement extends Element {

    private Group view;
    private ImageView database;

    public Sud_DatabaseElement(String id) {
        super(id);

        /* create a Group */
        view = new Group();

        /* Override Default-Values */
        height = 70;
        width = 70;
        nodeType = nodeType.sud_Database;

        /* load the image */
        Image image = new Image("file:frontend/src/resources/components/SystemUmweltDiagramIcons/sud_Database.png");
        database = new ImageView();
        database.setImage(image);
        database.setFitWidth(width);
        database.setFitHeight(height);

        /* add the components to the Group */
        view.getChildren().addAll(database);

        /* add the Group to a Node and add it as children of the Element */
        setView(view);
    }

    @Override
    public void setH(double height) {
        this.height = height;
        database.setFitHeight(height);
        getElementBorder().setH(height);
        relocateAllConnectors();
        updateSizeBackend();
    }

    @Override
    public void setW(double width) {
        this.width = width;
        database.setFitWidth(width);
        getElementBorder().setW(width);
        relocateAllConnectors();
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
