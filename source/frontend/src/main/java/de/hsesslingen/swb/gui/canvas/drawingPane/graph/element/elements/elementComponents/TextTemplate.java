package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;

public class TextTemplate {
    TextField textField;
    String text;

    public TextTemplate(double x, double y, double w, String text, int typ) {
        this.text = text;
        textField = new TextField(text);
        resizeText(x, y, w);
        textField.setBackground(Background.EMPTY);
        textField.setDisable(true);
        textField.setStyle("-fx-opacity: 1.0;");

        if (typ == 1) {
            textField.setAlignment(Pos.CENTER);
        } else {
            textField.setAlignment(Pos.BASELINE_LEFT);
        }

    }

    /**
     * returns the TextField
     *
     * @return TextField
     */
    public TextField getTextField() {
        return textField;
    }

    /**
     * set the TextField
     *
     * @param textField
     */
    public void setTextField(TextField textField) {
        this.textField = textField;
    }

    /**
     * get the TextTemplate of the TextField
     *
     * @return
     */
    public String getText() {
        return text;
    }


    /**
     * set the text of the TextField
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }


    public void resizeText(double x, double y, double w) {
        textField.relocate(x - w / 2 + 10, y - 12);
        textField.setPrefWidth(w - 20);
    }
}
