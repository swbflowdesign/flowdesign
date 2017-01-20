package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.dd_elements;

import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.TextTemplate;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.wire.wires.wireComponents.Arrow;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;

public class Dd_StraightRedArrowElement extends Element {

    private Group view;
    private TextTemplate labelField;
    private Arrow arrow;
    private Line line;
    private Rotate rz;
    private double angle;

    public Dd_StraightRedArrowElement(String id) {
        super(id);

        /* create a Group */
        view = new Group();

        /* Override Default-Values */
        height = 70;
        width = 70;
        nodeType = nodeType.dd_StraightRedArrow;

        /* create Arrow */
        line = new Line();
        line.setStrokeWidth(3);
        line.setStroke(Color.RED);
        line.setFill(Color.RED);
        line.setStartX(width);
        line.setStartY(height / 2);
        line.setEndX(0);
        line.setEndY(height / 2);
        arrow = new Arrow(line);
        arrow.setFill(Color.RED);

        angle = 0;
        rz = new Rotate();
        {
            rz.setAxis(Rotate.Z_AXIS);
            rz.setPivotX(width / 2);
            rz.setPivotY(height / 2);
        }
        getTransforms().addAll(rz);
        rz.setAngle(angle);

        /* add labelTextfield */
        label = "Text";
        labelField = new TextTemplate(width / 2, height / 3, width, label, 1);

        /* Add EventListener to the textfield */
        addLabelListener(labelField.getTextField());

        /* add the components to the Group */
        view.getChildren().addAll(line, arrow, labelField.getTextField());

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
        line.setStartY(height / 2);
        line.setEndY(height / 2);
        rz.setPivotY(height / 2);
        arrow.update();
        getElementBorder().setH(height);
        updateSizeBackend();
    }

    @Override
    public void setW(double width) {
        this.width = width;
        line.setStartX(width);
        rz.setPivotX(width / 2);
        arrow.update();
        getElementBorder().setW(width);
        labelField.resizeText(width / 2, height / 3, width);
        updateSizeBackend();
    }

    @Override
    public String getAdditional() {
        return "" + angle;
    }

    @Override
    public void setAdditional(String angle) {
        if (angle.length() != 0) {
            this.angle = Double.parseDouble(angle);
        } else {
            this.angle = 0;
        }
        rz.setAngle(this.angle);
        arrow.update();
        additional = angle;
        updateAdditionalBackend();
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

    @Override
    public double getCenterX() {
        return width / 2;
    }

    @Override
    public double getCenterY() {
        return height / 2;
    }
}
