package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.fd_elements;

import de.hsesslingen.swb.diagram.enums.nodeAttribut;
import de.hsesslingen.swb.diagram.enums.nodeColor;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.TextTemplate;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Fd_PortalElement extends Element {

    private Rectangle portal;
    private Group view;
    private TextTemplate labelField;
    private TextTemplate attributeField;


    public Fd_PortalElement(String id) {
        super(id);

        /* override default-values */
        width = 100;
        nodeType = nodeType.fd_Portal;

        /* create a Group */
        view = new Group();

        /* create portal-element */
        portal = new Rectangle();
        portal.setFill(Color.WHITE);
        portal.setStroke(Color.BLACK);
        portal.setWidth(width);
        portal.setHeight(height);

        /* add text to the element */
        label = "Label";
        labelField = new TextTemplate(width / 2, height / 2, width, label, 1);

        /* Add EventListener to the textfield */
        addLabelListener(labelField.getTextField());

        /* Add attribute Field */
        attributeField = new TextTemplate(width / 2, height / 2 + 16, 60, getStringOfAttribute(elementAttribut), 1);
        attributeField.getTextField().setStyle("-fx-font-size: 11;");

        /* add the components to the Group */
        view.getChildren().addAll(portal, labelField.getTextField(), attributeField.getTextField());

        /* add the Group to a Node and add it as children of the Element */
        setView(view);
    }

    @Override
    public String getLabel() {
        return label;
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
        portal.setFill(nodeColorToColor(nodeColor));
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
        portal.setHeight(height);
        getElementBorder().setH(height);
        relocateAllConnectors();
        labelField.resizeText(width / 2, height / 2, width);
        updateSizeBackend();
    }

    @Override
    public void setW(double width) {
        this.width = width;
        portal.setWidth(width);
        getElementBorder().setW(width);
        relocateAllConnectors();
        labelField.resizeText(width / 2, height / 2, width);
        updateSizeBackend();
    }

    @Override
    public void setLink(String link) {
        this.link = link;
        updateLinkBackend();
        if (link.equals("")) {
            portal.setStrokeWidth(1);
            portal.setStroke(Color.BLACK);
        } else {
            portal.setStrokeWidth(2);
            portal.setStroke(Color.GREEN);
        }
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
