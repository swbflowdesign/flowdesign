package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.fd_elements;

import de.hsesslingen.swb.diagram.enums.nodeAttribut;
import de.hsesslingen.swb.diagram.enums.nodeColor;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.TextTemplate;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.Tonne;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.Unit;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class Fd_UnitStateElement extends Element {

    private Group view;
    private Unit unit;
    private Tonne tonne;
    private TextTemplate labelField;
    private TextTemplate textTemplateField;
    private TextTemplate attributeField;

    public Fd_UnitStateElement(String id) {
        super(id);

        /* Override Default values */
        nodeType = nodeType.fd_UnitState;

        /* create a Group */
        view = new Group();

        /* create necessary components */
        unit = new Unit();
        tonne = new Tonne();

        /* get width and height from unit */
        width = unit.getW();
        height = unit.getH();

        /* place the elements */
        //unit is auto centered
        tonne.getView().relocate(getAngleXOfEllipse(), getAngleYOfEllipse() - tonne.getHeight() / 2);

        /* add text to the elements */
        label = "Label";
        labelField = new TextTemplate(0, 0, width, label, 1);                                           //1 for label (center alignment)

        /* Add EventListener to the textfield */
        addLabelListener(labelField.getTextField());

        text = "";
        textTemplateField = new TextTemplate(getAngleXOfEllipse() + tonne.getWidth() + 25, getAngleYOfEllipse(), width, text, 0);         //0 for text (left alignment)

        /* Add attribute Field */
        attributeField = new TextTemplate(0, 16, 60, getStringOfAttribute(elementAttribut), 1);
        attributeField.getTextField().setStyle("-fx-font-size: 11;");

        /* add the components to the Group */
        view.getChildren().addAll(unit.getView(), tonne.getView(), labelField.getTextField(), textTemplateField.getTextField(), attributeField.getTextField());

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
    public void setText(String text) {
        this.text = text;
        textTemplateField.getTextField().setText(text);
        updateTextBackend();
    }

    @Override
    public void setNodeColor(nodeColor nodeColor) {
        unit.getView().setFill(nodeColorToColor(nodeColor));
        this.elementColor = nodeColor;
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
        tonne.getView().relocate(getAngleXOfEllipse(), getAngleYOfEllipse() - tonne.getHeight() / 2);
        textTemplateField.getTextField().relocate(getAngleXOfEllipse() + 25, getAngleYOfEllipse() - 12);
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
        //textTemplateField.resizeText(width / 2 + tonne.getWidth(), 20, width);
        tonne.getView().relocate(getAngleXOfEllipse(), getAngleYOfEllipse() - tonne.getHeight() / 2);
        textTemplateField.getTextField().relocate(getAngleXOfEllipse() + 25, getAngleYOfEllipse() - 12);
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
