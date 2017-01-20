package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.dd_elements;

import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import javafx.scene.Group;
import javafx.scene.control.TextArea;

public class Dd_TextElement extends Element {

    private TextArea textElement;
    private Group view;

    public Dd_TextElement(String id) {
        super(id);

        /* create a Group */
        view = new Group();

        /* override Default-Values */
        height = 70;
        width = 70;
        nodeType = nodeType.dd_Text;
        text = "test";

        /* add text to the elements */

        textElement = new TextArea(text);
        textElement.setPrefRowCount(10);
        //textElement.setPrefColumnCount(100);
        textElement.setWrapText(true);
        textElement.setPrefWidth(width);
        textElement.setPrefHeight(height);
        textElement.setStyle("-fx-control-inner-background: yellow; -fx-font-size: 12;");

        /* add the components to the Group */
        view.getChildren().addAll(textElement);

        /* add the Group to a Node and add it as children of the Element */
        setView(view);
    }


    @Override
    public void setText(String text) {
        this.text = text;
        textElement.setText(text);
        updateTextBackend();
    }

    @Override
    public void setH(double height) {
        this.height = height;
        textElement.setPrefHeight(height);
        getElementBorder().setH(height);
        updateSizeBackend();
    }

    @Override
    public void setW(double width) {
        this.width = width;
        textElement.setPrefWidth(width);
        getElementBorder().setW(width);
        updateSizeBackend();
    }

    @Override
    public boolean activateTextField() {
        textElement.setEditable(true);
        textElement.requestFocus();
        return true;
    }

    @Override
    public boolean deactivateTextField() {
        textElement.setEditable(false);
        text = textElement.getText();
        updateLabelBackend();
        return true;
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
