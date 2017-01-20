package de.hsesslingen.swb.gui;

import de.hsesslingen.swb.diagram.enums.diagramType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * Created by lheft on 21.12.2016.
 */
public class CustomItem extends HBox {

    private Label boxText;
    private Button boxButton;
    private ImageView boxImageView;
    private diagramType type;

    CustomItem(Label txt, diagramType type) {
        super();

        this.boxText = txt;
        this.type = type;

        this.getChildren().add(boxText);
        this.setAlignment(Pos.CENTER_LEFT);
    }

    CustomItem(Label txt, Button bt) {
        super(5);

        this.boxText = txt;
        this.boxButton = bt;

        this.getChildren().addAll(boxText, boxButton);
        this.setAlignment(Pos.CENTER_LEFT);
    }

    CustomItem(ImageView img, Label txt) {
        super(5);

        this.boxImageView = img;
        this.boxText = txt;

        this.getChildren().addAll(boxImageView, boxText);
        this.setAlignment(Pos.CENTER_LEFT);
    }

    CustomItem(ImageView img, Label txt, Button bt, diagramType type) {
        super(5);

        this.boxImageView = img;
        this.boxText = txt;
        this.boxButton = bt;
        this.type = type;

        Region spacer = new Region();
        this.setHgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(boxImageView, boxText, spacer, boxButton);
        this.setAlignment(Pos.CENTER_LEFT);
    }

    public String getLabel() {
        return boxText.getText();
    }

    public void setLabel(String label) {
        this.boxText.setText(label);
    }

    public diagramType getType() {
        return type;
    }
}