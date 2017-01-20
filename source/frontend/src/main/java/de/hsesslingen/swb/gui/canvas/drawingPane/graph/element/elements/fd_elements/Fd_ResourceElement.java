package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.fd_elements;

import de.hsesslingen.swb.diagram.enums.nodeColor;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.TextTemplate;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Fd_ResourceElement extends Element {

    private Group view;
    private Polygon resource;
    private TextTemplate labelField;

    public Fd_ResourceElement(String id) {
        super(id);

        /* create a group */
        view = new Group();

        /* override default-values */
        nodeType = nodeType.fd_Resource;

        /* create the resource element */
        resource = new Polygon(width / 2, 0, width, height, 0, height);
        resource.setStroke(Color.BLACK);
        resource.setFill(Color.WHITE);

        /* add text to the elements */
        label = "Label";
        labelField = new TextTemplate(width / 2, height + 7, width + width / 2, label, 1);

        /* Add EventListener to the textfield */
        addLabelListener(labelField.getTextField());

        /* add the components to the Group */
        view.getChildren().addAll(resource, labelField.getTextField());

        /* add the Group to a Node and add it as children of the Element */
        setView(view);
    }

    @Override
    public void setLabel(String text) {
        this.label = text;
        labelField.getTextField().setText(text);
        updateLabelBackend();
    }

    @Override
    public void setNodeColor(nodeColor nodeColor) {
        this.elementColor = nodeColor;
        resource.setFill(nodeColorToColor(nodeColor));
        updateColorBackend();
    }


    @Override
    public void setH(double height) {
        this.height = height;
        resource.getPoints().setAll(width / 2, 0.0, width, height, 0.0, height);
        getElementBorder().setH(height);
        relocateAllConnectors();
        labelField.resizeText(width / 2, height + 7, width + width / 2);
        updateSizeBackend();
    }

    @Override
    public void setW(double width) {
        this.width = width;
        resource.getPoints().setAll(width / 2, 0.0, width, height, 0.0, height);
        getElementBorder().setW(width);
        relocateAllConnectors();
        labelField.resizeText(width / 2, height + 7, width + width / 2);
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

    @Override
    public boolean activateTextField() {
        labelField.getTextField().setDisable(false);
        labelField.getTextField().setEditable(true);
        labelField.getTextField().requestFocus();
        return true;
    }

    @Override
    public boolean deactivateTextField() {
        labelField.getTextField().setDisable(true);
        labelField.getTextField().setEditable(false);
        text = labelField.getTextField().getText();
        updateLabelBackend();
        return true;
    }

    @Override
    public TextField getTextField() {
        return labelField.getTextField();
    }
}

