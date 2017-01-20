package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.dd_elements;

import de.hsesslingen.swb.diagram.enums.nodeColor;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.TextTemplate;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static de.hsesslingen.swb.gui.FlowProperties.refreshProperties;

public class Dd_SquareElement extends Element {

    private Rectangle square;
    private Group view;
    private TextTemplate labelField;

    public Dd_SquareElement(String id) {
        super(id);

        /* override default-values */
        width = 100;
        nodeType = nodeType.dd_Square;

        /* create a Group */
        view = new Group();

        /* create portal-element */
        square = new Rectangle();
        square.setFill(Color.WHITE);
        square.setStroke(Color.BLACK);
        square.setWidth(width);
        square.setHeight(height);

        /* add text to the element */
        label = "Label";
        labelField = new TextTemplate(width / 2, height / 2, width, label, 1);

        /* Add EventListener to the textfield */
        labelField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
            label = newValue;
            updateLabelBackend();
            refreshProperties(this);
        });

        /* add the components to the Group */
        view.getChildren().addAll(square, labelField.getTextField());

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
        square.setFill(nodeColorToColor(nodeColor));
        updateColorBackend();
    }

    @Override
    public void setH(double height) {
        this.height = height;
        square.setHeight(height);
        getElementBorder().setH(height);
        relocateAllConnectors();
        labelField.resizeText(width / 2, height / 2, width);
        updateSizeBackend();
    }

    @Override
    public void setW(double width) {
        this.width = width;
        square.setWidth(width);
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
            square.setStrokeWidth(1);
            square.setStroke(Color.BLACK);
        } else {
            square.setStrokeWidth(2);
            square.setStroke(Color.GREEN);
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
