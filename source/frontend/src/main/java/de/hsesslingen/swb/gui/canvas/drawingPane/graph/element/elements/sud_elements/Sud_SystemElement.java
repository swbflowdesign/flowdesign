package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.sud_elements;

import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.TextTemplate;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Sud_SystemElement extends Element {

    private Group view;
    private Circle system;
    private TextTemplate labelField;

    public Sud_SystemElement(String id) {
        super(id);

        /* create a Group */
        view = new Group();

        /* override Default-Values */
        width = 200;
        height = 200;
        nodeType = nodeType.sud_System;

        system = new Circle(width / 2);
        system.setStroke(Color.BLACK);
        system.setStrokeWidth(2);
        system.setFill(Color.WHITE);

        /* Label */
        label = "Label";
        labelField = new TextTemplate(0, 0, width, label, 1);

        /* Add EventListener to the textfield */
        addLabelListener(labelField.getTextField());

        /* add the components to the Group */
        view.getChildren().addAll(system, labelField.getTextField());


        /* add the Group to a Node and add it as children of the Element */
        setView(view);
    }

    @Override
    public void setH(double height) {
        this.height = height;
        this.width = height;
        system.setRadius(height / 2);
        getElementBorder().setH(height);
        getElementBorder().setW(height);
        relocateAllConnectors();
        labelField.resizeText(0, 0, width);
        getElementBorder().relocate(-width / 2, -height / 2);
        updateSizeBackend();
    }

    @Override
    public void setW(double width) {
        this.width = width;
        this.height = width;
        system.setRadius(width / 2);
        getElementBorder().setW(width);
        getElementBorder().setH(width);
        labelField.resizeText(0, 0, width);
        relocateAllConnectors();
        getElementBorder().relocate(-width / 2, -height / 2);
        updateSizeBackend();
    }

    @Override
    public double correctionOfX() {
        return width / 2;
    }

    @Override
    public double correctionOfY() {
        return height / 2;
    }

    @Override
    public void setLabel(String text) {
        label = text;
        labelField.getTextField().setText(text);
        updateLabelBackend();
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
        label = labelField.getTextField().getText();
        updateLabelBackend();
        return true;
    }

    @Override
    public TextField getTextField() {
        return labelField.getTextField();
    }
}
