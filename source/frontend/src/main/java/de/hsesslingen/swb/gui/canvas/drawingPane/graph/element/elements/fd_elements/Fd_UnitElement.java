package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.fd_elements;

import de.hsesslingen.swb.diagram.enums.nodeAttribut;
import de.hsesslingen.swb.diagram.enums.nodeColor;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.TextTemplate;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.Unit;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class Fd_UnitElement extends Element {

    private Group view;
    private Unit unit;
    private TextTemplate labelField;
    private TextTemplate attributeField;

    public Fd_UnitElement(String id) {
        super(id);

        /* Override default values */
        nodeType = nodeType.fd_Unit;

        /* create a Group */
        view = new Group();

        /* create necessary components */
        unit = new Unit();

        /* get width and height from unit */
        width = unit.getW();
        height = unit.getH();

        /* place the element */
        //unit is auto centered

        /* add text to the elements */
        label = "Label";
        labelField = new TextTemplate(0, 0, width, label, 1);                              //1 for label (center alignment)

        /* Add EventListener to the textfield */
        addLabelListener(labelField.getTextField());

        /* Add attribute Field */
        attributeField = new TextTemplate(0, 16, 60, getStringOfAttribute(elementAttribut), 1);
        attributeField.getTextField().setStyle("-fx-font-size: 11;");

        /* add the components to the Group */
        view.getChildren().addAll(unit.getView(), labelField.getTextField(), attributeField.getTextField());

        /* add the Group to a Node and add it as children of the Element */
        setView(view);
    }

    @Override
    public void setLabel(String text) {
        label = text;
        labelField.getTextField().setText(text);
        updateLabelBackend();
    }

    @Override
    public void setNodeColor(nodeColor nodeColor) {
        this.elementColor = nodeColor;
        unit.getView().setFill(nodeColorToColor(nodeColor));
        updateColorBackend();
    }

    @Override
    public void setNodeAttribut(nodeAttribut elementAttribute) {
        this.elementAttribut = elementAttribute;
        attributeField.getTextField().setText(getStringOfAttribute(elementAttribute));
    }

    @Override
    public void setH(double height) {
        this.height = height;
        unit.setH(height);
        getElementBorder().setH(height);
        getElementBorder().relocate(-width / 2, -height / 2);
        relocateAllConnectors();
        updateSizeBackend();
    }

    @Override
    public void setW(double width) {
        this.width = width;
        unit.setW(width);
        getElementBorder().setW(width);
        getElementBorder().relocate(-width / 2, -height / 2);
        relocateAllConnectors();
        labelField.resizeText(0, 0, width);
        updateSizeBackend();
    }

    @Override
    public void setLink(String link) {
        this.link = link;
        updateLinkBackend();
        if (link.equals("")) {
            unit.getView().setStrokeWidth(1);
            unit.getView().setStroke(Color.BLACK);
        } else {
            unit.getView().setStrokeWidth(2);
            unit.getView().setStroke(Color.GREEN);
        }
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
