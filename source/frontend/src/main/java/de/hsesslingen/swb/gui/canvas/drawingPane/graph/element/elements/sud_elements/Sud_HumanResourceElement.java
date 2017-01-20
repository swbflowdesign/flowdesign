package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.sud_elements;

import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.TextTemplate;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sud_HumanResourceElement extends Element {

    private Group view;
    private ImageView humanResource;
    private TextTemplate labelField;

    public Sud_HumanResourceElement(String id) {
        super(id);

        /* create a Group */
        view = new Group();

        /* Override Default-Values */
        height = 70;
        width = 70;
        nodeType = nodeType.sud_HumanResource;

        /* load the image */
        Image image = new Image("file:frontend/src/resources/components/SystemUmweltDiagramIcons/sud_HumanResource.png");
        humanResource = new ImageView();
        humanResource.setImage(image);
        humanResource.setFitWidth(width);
        humanResource.setFitHeight(height);

        /* Label */
        label = "Label";
        labelField = new TextTemplate(width / 2, height + 10, width, label, 1);

        /* Add EventListener to the textfield */
        addLabelListener(labelField.getTextField());

        /* add the components to the Group */
        view.getChildren().addAll(humanResource, labelField.getTextField());

        /* add the Group to a Node and add it as children of the Element */
        setView(view);
    }

    @Override
    public void setH(double height) {
        this.height = height;
        humanResource.setFitHeight(height);
        getElementBorder().setH(height);
        labelField.resizeText(width / 2, height + 10, width);
        relocateAllConnectors();
        updateSizeBackend();
    }

    @Override
    public void setW(double width) {
        this.width = width;
        humanResource.setFitWidth(width);
        getElementBorder().setW(width);
        labelField.resizeText(width / 2, height + 10, width);
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
