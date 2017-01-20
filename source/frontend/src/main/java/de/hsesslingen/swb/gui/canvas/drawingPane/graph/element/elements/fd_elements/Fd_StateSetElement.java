package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.fd_elements;

import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.TextTemplate;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Fd_StateSetElement extends Element {

    private Group view;
    private ImageView stateSet;
    private TextTemplate labelField;

    public Fd_StateSetElement(String id) {
        super(id);

        /* create a Group */
        view = new Group();

        /* override Default-Values */
        height = 70;
        width = 70;
        nodeType = nodeType.fd_StateSet;

        /* load the image */
        Image image = new Image("file:frontend/src/resources/components/FlowDesignIcons/fd_StateSet.png");
        stateSet = new ImageView();
        stateSet.setImage(image);
        stateSet.setFitWidth(width);
        stateSet.setFitHeight(height);

        /* add text to the elements */
        label = "Label";
        labelField = new TextTemplate(width / 2, height - 10, width, label, 1);

        /* Add EventListener to the textfield */
        addLabelListener(labelField.getTextField());

        /* add the components to the Group */
        view.getChildren().addAll(stateSet, labelField.getTextField());

        /* add the Group to a Node and add it as children of the Element */
        setView(view);
    }

    @Override
    public void setLabel(String text) {
        labelField.getTextField().setText(text);
        updateLabelBackend();
    }

    @Override
    public void setH(double height) {
        this.height = height;
        stateSet.setFitHeight(height);
        labelField.resizeText(width / 2, height - 10, width);
        getElementBorder().setH(height);
        relocateAllConnectors();
        updateSizeBackend();
    }

    @Override
    public void setW(double width) {
        this.width = width;
        stateSet.setFitWidth(width);
        labelField.resizeText(width / 2, height - 10, width);
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
