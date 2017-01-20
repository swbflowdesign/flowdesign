package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.fd_elements;

import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.TextTemplate;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Fd_StateGetElement extends Element {
    private Group view;
    private ImageView stateGet;
    private TextTemplate labelField;

    public Fd_StateGetElement(String id) {
        super(id);

        /* create a Group */
        view = new Group();

        /* Override Default-Values */
        height = 70;
        width = 70;
        nodeType = nodeType.fd_StateGet;

        /* load the image */
        Image image = new Image("file:frontend/src/resources/components/FlowDesignIcons/fd_StateGet.png");
        stateGet = new ImageView();
        stateGet.setImage(image);
        stateGet.setFitWidth(width);
        stateGet.setFitHeight(height);

        /* add text to the elements */
        label = "Label";
        labelField = new TextTemplate(width / 2, height - 10, width, label, 1);

        /* Add EventListener to the textfield */
        addLabelListener(labelField.getTextField());

        /* add the components to the Group */
        view.getChildren().addAll(stateGet, labelField.getTextField());

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
    public void setH(double height) {
        this.height = height;
        stateGet.setFitHeight(height);
        labelField.resizeText(width / 2, height - 10, width);
        getElementBorder().setH(height);
        relocateAllConnectors();
        updateSizeBackend();
    }

    @Override
    public void setW(double width) {
        this.width = width;
        stateGet.setFitWidth(width);
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
