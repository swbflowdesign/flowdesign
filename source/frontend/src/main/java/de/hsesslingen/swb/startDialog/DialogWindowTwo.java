package de.hsesslingen.swb.startDialog;

import de.hsesslingen.swb.App;
import de.hsesslingen.swb.BackendAPI;
import de.hsesslingen.swb.diagram.enums.diagramType;
import de.hsesslingen.swb.fileSystem.directoryChooser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.File;
import java.util.List;


public class DialogWindowTwo {

    public static Scene scene;
    public static Button abortBtn;


    /**
     * Creates the second DialogWindow scene and fills it with all of it's components (except the handler for the abortBtn)
     *
     * @return Scene
     */
    public static Scene getScene() {
        /* Create a BorderPane */
        BorderPane root = new BorderPane();
        /* Bottom */
        HBox bottom = new HBox();
        bottom.setStyle("-fx-background-color: #585858");
        bottom.setPrefSize(700, 50);
        bottom.setSpacing(300);
        bottom.setPadding(new Insets(12.5, 0, 0, 100));

        /* Left */
        Pane left = new BorderPane();
        left.setStyle("-fx-background-color: deepskyblue");
        left.setMinSize(100, 390);
        /* Right */
        Pane right = new BorderPane();
        right.setStyle("-fx-background-color: deepskyblue");
        right.setMinSize(100, 390);

        /* Main Area */
        VBox mainArea = new VBox();
        mainArea.setPrefSize(400, 390);
        mainArea.setSpacing(50);
        mainArea.setAlignment(Pos.CENTER);

        /* Title */
        HBox titleBox = new HBox();
        Text title = new Text("Neues Projekt");
        title.setStyle("-fx-font: 20 bold;");
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(20, 0, 0, 0));
        titleBox.getChildren().add(title);

        /* Settings */
        VBox settings = new VBox();
        settings.setAlignment(Pos.CENTER);

        /* 'Projektname' Area */
        HBox projektnameArea = new HBox();
        projektnameArea.setAlignment(Pos.CENTER);
        VBox projektnameBox = new VBox();
        Text projektname = new Text("Projektname:");
        projektnameBox.getChildren().add(projektname);
        projektnameBox.setMinWidth(100);
        TextField projektnameTxtField = new TextField();
        projektnameTxtField.setPromptText("neues Projekt");
        projektnameTxtField.setMinWidth(300);
        Pane invisibleStylingPane = new BorderPane();
        invisibleStylingPane.setMinWidth(25);
        projektnameArea.getChildren().add(projektnameBox);
        projektnameArea.getChildren().add(projektnameTxtField);
        projektnameArea.getChildren().add(invisibleStylingPane);


        /* 'Speicherort' Area */
        HBox selectProjectPathArea = new HBox();
        selectProjectPathArea.setAlignment(Pos.CENTER);
        VBox selectProjectPathTextBox = new VBox();
        Text selectProjectPathText = new Text("Speicherort:");
        selectProjectPathTextBox.getChildren().add(selectProjectPathText);
        selectProjectPathTextBox.setMinWidth(100);
        TextField selectProjectPathTextField = new TextField();
        selectProjectPathTextField.setPromptText("C:" + File.separator + "User" + File.separator + System.getProperty("user.name") + File.separator + "Desktop" + File.separator + "FlowDesign" + File.separator + projektnameTxtField.getText());
        selectProjectPathTextField.setMinWidth(300);

        /* Button to select a directory for the project */
        Button setSpeicherortBtn = new Button("...");
        setSpeicherortBtn.setOnAction(new EventHandler<ActionEvent>() {
            //@Override
            public void handle(ActionEvent e) {

                File directory = directoryChooser.showDirectoryDialog();
                if (directory != null)
                    selectProjectPathTextField.setText(directory.toString());

            }

        });
        setSpeicherortBtn.setMinWidth(20);
        selectProjectPathArea.getChildren().add(selectProjectPathTextBox);
        selectProjectPathArea.getChildren().add(selectProjectPathTextField);
        selectProjectPathArea.getChildren().add(setSpeicherortBtn);
        settings.getChildren().add(projektnameArea);
        settings.getChildren().add(selectProjectPathArea);


        /* Diagram generation Checkboxes */
        HBox diagramGenerateCheckboxesArea = new HBox();
        diagramGenerateCheckboxesArea.setAlignment(Pos.CENTER);
        CheckBoxTreeItem<String> createSystemUmweltDiagram = new CheckBoxTreeItem<>("System Umwelt Diagramm erstellen");
        CheckBoxTreeItem<String> createDialogDiagram = new CheckBoxTreeItem<>("Dialog Diagramm erstellen");
        CheckBoxTreeItem<String> createFlowDesignDiagram = new CheckBoxTreeItem<>("Flow Design Diagramm erstellen");

        CheckBoxTreeItem<String> createAllDiagrams = new CheckBoxTreeItem<>("Alle Diagrammarten erstellen");
        TreeView<String> createAllDiagramsTree = new TreeView<>(createAllDiagrams);
        createAllDiagrams.getChildren().add(createSystemUmweltDiagram);
        createAllDiagrams.getChildren().add(createDialogDiagram);
        createAllDiagrams.getChildren().add(createFlowDesignDiagram);
        createAllDiagrams.setExpanded(true);

        createAllDiagramsTree.setCellFactory(CheckBoxTreeCell.<String>forTreeView());
        diagramGenerateCheckboxesArea.getChildren().add(createAllDiagramsTree);
        diagramGenerateCheckboxesArea.setStyle("-fx-background-color: #f4f4f4;");
        diagramGenerateCheckboxesArea.setBackground(new Background(new BackgroundFill(Color.web("#f4f4f4"), CornerRadii.EMPTY, Insets.EMPTY)));
        createAllDiagramsTree.setBackground(new Background(new BackgroundFill(Color.web("#f4f4f4"), CornerRadii.EMPTY, Insets.EMPTY)));
        createAllDiagramsTree.setStyle("-fx-background: #f4f4f4; -fx-background-color: #f4f4f4;");
        createAllDiagramsTree.setMinWidth(400);

        mainArea.getChildren().add(titleBox);
        mainArea.getChildren().add(settings);
        mainArea.getChildren().add(diagramGenerateCheckboxesArea);


        root.setBottom(bottom);
        root.setCenter(mainArea);
        root.setLeft(left);
        root.setRight(right);


        // Set the Size of Borderpane
        root.setPrefSize(700, 440);


        //Add 'Abbrechen' button
        abortBtn = new Button("Abbrechen");
        bottom.getChildren().add(abortBtn);


        // Add 'Create Button' button
        Button createProjectBtn = new Button("Erstellen");
        createProjectBtn.setOnAction(new EventHandler<ActionEvent>() {
            //@Override
            public void handle(ActionEvent e) {
                String createProjectWithThisName = projektnameTxtField.getText();
                String createProjectWithThisPath = selectProjectPathTextField.getText();

                List selectedItems = createAllDiagramsTree.getSelectionModel().getSelectedItems();

                if (!createProjectWithThisName.equals("")) {
                    if (!createProjectWithThisPath.equals("")) {
                        if (BackendAPI.project().createNewProject(createProjectWithThisPath, createProjectWithThisName)) {

                            //get checked TreeItems and create the Diagrams
                            if (createFlowDesignDiagram.isSelected()) {
                                BackendAPI.diagram().createDiagram("FlowDesignDiagramm", diagramType.FlowDesign);
                            }
                            if (createDialogDiagram.isSelected()) {
                                BackendAPI.diagram().createDiagram("DialogDiagramm", diagramType.DialogDiagram);
                            }
                            if (createSystemUmweltDiagram.isSelected()) {
                                BackendAPI.diagram().createDiagram("SystemUmweltDiagramm", diagramType.SystemUmweltDiagram);
                            }
                            BackendAPI.project().saveProject();

                            // Show Main Window
                            App.openMain();
                        }
                    } else {
                        System.out.println("No Path selected");
                        //TODO: default folder
                        //creates new foler on desktop/Flowdesign and saves project there.
                        //backendAPI.project().createNewProject(selectProjectPathTextField.getPromptText(), createProjectWithThisName);
                    }
                } else {
                    System.out.println("No Name selected");
                }
            }
        });
        createProjectBtn.setPrefSize(100, 25);
        bottom.getChildren().add(createProjectBtn);


        /* Scene2 = New Project Dialog Window */
        scene = new Scene(root);

        return scene;
    }
}

