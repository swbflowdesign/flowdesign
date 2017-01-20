package de.hsesslingen.swb.gui;

import de.hsesslingen.swb.BackendAPI;
import de.hsesslingen.swb.DataStorage;
import de.hsesslingen.swb.diagram.diagram;
import de.hsesslingen.swb.diagram.enums.*;
import de.hsesslingen.swb.diagram.version;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.connectors.Connector;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.List;
import java.util.Optional;

/**
 * Created by lheft on 02.11.2016.
 */
public class FlowProperties {

    private static Element element = null;
    private static ScrollPane scrollPane = new ScrollPane();
    private static VBox box = new VBox();
    private static Text heightDesc = new Text("Höhe");
    private static Text widthDesc = new Text("Breite");
    private static Text labelDesc = new Text("Label");
    private static Text textDesc = new Text("Text");
    private static Text colorDesc = new Text("Farbe");
    private static Text posXDesc = new Text("Position X");
    private static Text posYDesc = new Text("Position Y");
    private static Text angleDesc = new Text("Winkel");
    private static Text additionalDesc = new Text("Extra");
    private static Text attributDesc = new Text("Attribut");
    private static Text zIndexDesc = new Text("Z-Index");
    private static Text dataInputDesc = new Text("Input Type");
    private static Text dataOutputDesc = new Text("Output Type");
    private static Text diagramNameDesc = new Text("Diagramm Name");
    private static Text versionDesc = new Text("Version");
    private static Text leftConnectorsDesc = new Text("Anzahl Inputkonektoren");
    private static Text rightConnectorsDesc = new Text("Anzahl Outputkonektoren");
    private static Text linkDesc = new Text("Link auf anderes Diagramm");
    private static Text parentDesc = new Text("Parent");
    private static TextField posX = new TextField();
    private static TextField posY = new TextField();
    private static TextField angle = new TextField();
    private static TextField height = new TextField();
    private static TextField width = new TextField();
    private static TextField label = new TextField();
    private static TextField text = new TextField();
    private static Button changeDiagramName = new Button("Namen ändern...");
    private static ComboBox color = new ComboBox();
    private static ComboBox version = new ComboBox();
    private static ComboBox parent = new ComboBox();
    private static Button newVersion = new Button("Neue Version hinzufügen");
    private static Button leftConnectorsAdd = new Button("+");
    private static Button leftConnectorsRemove = new Button("-");
    private static Button rightConnectorsAdd = new Button("+");
    private static Button rightConnectorsRemove = new Button("-");
    private static TextField additional = new TextField();
    private static ComboBox attribut = new ComboBox();
    private static Button bringToFront = new Button("In den Vordergrund");
    private static Button sendToBack = new Button("In den Hintergrund");
    private static Tab properties = new Tab("Eigenschaften");
    private static VBox dataInput = new VBox();
    private static VBox dataOutput = new VBox();
    private static ComboBox link = new ComboBox();
    private static FilteredList<String> filteredData;
    private static TextField filterInput = new TextField();

    public static Tab getProperties() {
        return properties;
    }

    /**
     * initializes property tab + calls initJavafxComponents()
     */
    public static void initProperties() {
        initJavafxComponents();

        // Box styling
        box.setPadding(new Insets(10, 10, 10, 10));
        box.setSpacing(20);

        //add box to a scrollpane
        scrollPane.setContent(box);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        properties.setClosable(false);
        properties.setContent(scrollPane);
    }

    /**
     * initializes Changelisteners and Eventhandlers (gets called by initProperties(), dont use this use initProperties())
     */
    private static void initJavafxComponents() {

        //set available colors
        for (nodeColor clr : nodeColor.values()) {
            if (clr != nodeColor.Black)
                color.getItems().add(clr);
        }

        //changing the selected item in the combobox will set the nodeColor of the element
        color.valueProperty().addListener(new ChangeListener<nodeColor>() {
            public void changed(ObservableValue ov, nodeColor old, nodeColor selected) {
                element.setNodeColor(selected);
            }
        });

        //set available nodeAttributes
        for (nodeAttribut attr : nodeAttribut.values()) {
            attribut.getItems().add(attr);
        }

        //changing the selected item in the combobox will set the nodeAttribut of the element
        attribut.valueProperty().addListener(new ChangeListener<nodeAttribut>() {
            public void changed(ObservableValue ov, nodeAttribut old, nodeAttribut selected) {
                element.setNodeAttribut(selected);
            }
        });

        //on enter set posX of element
        posX.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    if (posX.getText().matches("-?\\d+(\\.\\d+)?")) {
                        if (element.getElementType() == nodeType.fd_Unit ||
                                element.getElementType() == nodeType.fd_ResourceOval ||
                                element.getElementType() == nodeType.fd_UnitState ||
                                element.getElementType() == nodeType.fd_SourceOfFlow ||
                                element.getElementType() == nodeType.fd_SinkOfFlow ||
                                element.getElementType() == nodeType.fd_Iteration) {
                            element.relocate(Double.parseDouble(posX.getText()) + element.getW() / 2, element.getY());
                        } else {
                            element.relocate(Double.parseDouble(posX.getText()), element.getY());
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Bitte eine valide Zahl eingeben.");

                        alert.showAndWait();
                    }
                }
            }
        });

        //on enter set posY of element
        posY.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    if (posY.getText().matches("-?\\d+(\\.\\d+)?")) {
                        if (element.getElementType() == nodeType.fd_Unit ||
                                element.getElementType() == nodeType.fd_ResourceOval ||
                                element.getElementType() == nodeType.fd_UnitState ||
                                element.getElementType() == nodeType.fd_SourceOfFlow ||
                                element.getElementType() == nodeType.fd_SinkOfFlow ||
                                element.getElementType() == nodeType.fd_Iteration) {
                            element.relocate(element.getX(), Double.parseDouble(posY.getText()) + element.getH() / 2);
                        } else {
                            element.relocate(element.getX(), Double.parseDouble(posY.getText()));
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Bitte eine valide Zahl eingeben.");

                        alert.showAndWait();
                    }
                }
            }
        });

        //on enter set Angle of StraightRedArrow
        angle.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    element.setAdditional(angle.getText());
                }
            }
        });

        //make numeric Textfield only
        height.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d+")) {
                    height.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        //on enter set height of element
        height.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    element.setH(Double.parseDouble(height.getText()));
                }
            }
        });

        //make numeric Textfield only
        width.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d+?")) {
                    width.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        //on enter set width of element
        width.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    element.setW(Double.parseDouble(width.getText()));
                }
            }
        });

        //on enter set label of element
        label.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    element.setLabel(label.getText());
                }
            }
        });

        //on enter set text of element
        text.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    element.setText(text.getText());
                }
            }
        });

        //on enter set additional of element
        additional.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    element.setAdditional(additional.getText());
                }
            }
        });

        //on press bring the element to front
        bringToFront.setOnAction(new EventHandler<ActionEvent>() {
            //@Override
            public void handle(ActionEvent e) {
                element.toFront();
            }
        });

        //on press send element to the back
        sendToBack.setOnAction(new EventHandler<ActionEvent>() {
            //@Override
            public void handle(ActionEvent e) {
                element.toBack();
            }
        });

        //on click add left connector to element
        leftConnectorsAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                element.addLeftConnector(null);
                refreshProperties(element);
            }
        });

        //on click add left connector to element
        leftConnectorsRemove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                element.removeLeftConnector();
                refreshProperties(element);
            }
        });

        //on click add right connector to element
        rightConnectorsAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                element.addRightConnector(null);
                refreshProperties(element);
            }
        });

        //on click add right connector to element
        rightConnectorsRemove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                element.removeRightConnector();
                refreshProperties(element);
            }
        });

        //on click rename diagram
        changeDiagramName.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Actions.renameDiagram(DataStorage.activeDiagram.getName());
            }
        });

        //on enter add new diagram
        newVersion.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String diagName = DataStorage.activeDiagram.getName();

                // Add version
                BackendAPI.diagram().addVersion(diagName);

                // Reload diagram
                Actions.closeDiagram(diagName);
                Actions.openDiagram(diagName);
            }
        });

        //on selecting a version load it
        version.valueProperty().addListener(new ChangeListener<Integer>() {
            public void changed(ObservableValue ov, Integer old, Integer selected) {
                if (selected != null && selected != DataStorage.activeDiagram.getActiveVersionNum()) {
                    DataStorage.activeDiagram.setActiveVersionNum(selected);

                    // Reload diagram
                    String diagName = DataStorage.activeDiagram.getName();
                    Actions.closeDiagram(diagName);
                    Actions.openDiagram(diagName);
                }
            }
        });

        //on selecting a link save it
        link.valueProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue ov, String old, String selected) {
                if (link.getItems().size() > 0) {
                    if (selected != null && DataStorage.project.getDiagram(selected) != null) {
                        element.setLink(selected);
                    } else {
                        element.setLink("");
                    }
                }
            }
        });

        //on selecting a parent save it
        parent.valueProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue ov, String old, String selected) {
                if (parent.getItems().size() > 0) {
                    if (selected != null && DataStorage.project.getDiagram(selected) != null) {
                        DataStorage.activeDiagram.setParent(selected);
                        FlowFileTree.updateFileTree();
                    } else {
                        DataStorage.activeDiagram.setParent("root");
                        FlowFileTree.updateFileTree();
                    }
                }
            }
        });
    }

    /**
     * adds the position to the property tab
     */
    private static void addPosition() {
        if (element.getElementType() == nodeType.fd_Unit ||
                element.getElementType() == nodeType.fd_ResourceOval ||
                element.getElementType() == nodeType.fd_UnitState ||
                element.getElementType() == nodeType.fd_SourceOfFlow ||
                element.getElementType() == nodeType.fd_SinkOfFlow ||
                element.getElementType() == nodeType.fd_Iteration) {
            posX.setText(String.valueOf(element.getX() - element.getW() / 2));
            posY.setText(String.valueOf(element.getY() - element.getH() / 2));
        } else {
            posX.setText(String.valueOf(element.getX()));
            posY.setText(String.valueOf(element.getY()));
        }

        posXDesc.setFont(Font.font(null, FontWeight.BOLD, 12));
        posYDesc.setFont(Font.font(null, FontWeight.BOLD, 12));

        VBox posBox = new VBox();
        posBox.setSpacing(3);
        posBox.getChildren().addAll(posXDesc, posX, posYDesc, posY);
        box.getChildren().addAll(posBox);
    }

    /**
     * adds the size of element to the property tab
     */
    private static void addSize() {
        height.setText(String.valueOf(element.getH()).split("\\.")[0]);
        width.setText(String.valueOf(element.getW()).split("\\.")[0]);

        heightDesc.setFont(Font.font(null, FontWeight.BOLD, 12));
        widthDesc.setFont(Font.font(null, FontWeight.BOLD, 12));

        VBox sizeBox = new VBox();
        sizeBox.setSpacing(3);
        sizeBox.getChildren().addAll(heightDesc, height, widthDesc, width);
        box.getChildren().addAll(sizeBox);
    }

    /**
     * adds the angle of the StraightRedArrow to the property tab
     */
    private static void addAngle() {
        angle.setText(element.getAdditional().split("\\.")[0]);

        angleDesc.setFont(Font.font(null, FontWeight.BOLD, 12));

        VBox angleBox = new VBox();
        angleBox.setSpacing(3);
        angleBox.getChildren().addAll(angleDesc, angle);
        box.getChildren().addAll(angleBox);
    }

    /**
     * adds the label to the property tab
     */
    private static void addLabel() {
        label.setText(element.getLabel());

        labelDesc.setFont(Font.font(null, FontWeight.BOLD, 12));

        VBox labelBox = new VBox();
        labelBox.setSpacing(3);
        labelBox.getChildren().addAll(labelDesc, label);
        box.getChildren().addAll(labelBox);
    }

    /**
     * adds the text to the property tab
     */
    private static void addText() {
        text.setText(element.getText());

        textDesc.setFont(Font.font(null, FontWeight.BOLD, 12));

        VBox textBox = new VBox();
        textBox.setSpacing(3);
        textBox.getChildren().addAll(textDesc, text);
        box.getChildren().addAll(textBox);
    }

    /**
     * adds the color to the property tab
     */
    private static void addColor() {
        color.getSelectionModel().select(element.getNodeColor());

        colorDesc.setFont(Font.font(null, FontWeight.BOLD, 12));

        VBox colorBox = new VBox();
        colorBox.setSpacing(3);
        colorBox.getChildren().addAll(colorDesc, color);
        box.getChildren().addAll(colorBox);
    }

    /**
     * adds additional information of element to the property tab
     */
    private static void addAdditional() {
        additional.setText(element.getAdditional());

        additionalDesc.setFont(Font.font(null, FontWeight.BOLD, 12));

        VBox additionalBox = new VBox();
        additionalBox.setSpacing(3);
        additionalBox.getChildren().addAll(additionalDesc, additional);
        box.getChildren().addAll(additionalBox);
    }

    /**
     * adds nodeAttribut of element to the property tab
     */
    private static void addNodeAttribut() {
        attribut.getSelectionModel().select(element.getAttribut());

        attributDesc.setFont(Font.font(null, FontWeight.BOLD, 12));

        VBox attributeBox = new VBox();
        attributeBox.setSpacing(3);
        attributeBox.getChildren().addAll(attributDesc, attribut);
        box.getChildren().addAll(attributeBox);
    }

    /**
     * add z-index controls to the property tab
     */
    private static void addZIndexControls() {
        zIndexDesc.setFont(Font.font(null, FontWeight.BOLD, 12));

        VBox zIndexBox = new VBox();
        zIndexBox.setSpacing(3);
        zIndexBox.getChildren().addAll(zIndexDesc, bringToFront, sendToBack);
        box.getChildren().addAll(zIndexBox);
    }

    /**
     * add dataInput of element to the property tab
     */
    private static void addDataInput() {
        dataInputDesc.setFont(Font.font(null, FontWeight.BOLD, 12));
        dataInput.getChildren().clear();

        int zaehl = 0;

        //create input fields and set data type
        List<Connector> con = element.getConnectors();
        if (con != null) {
            for (Connector connection : con) {
                if (connection.getConnectorOrientation() == connectorOrientation.left) {

                    //creates text field and input field
                    zaehl++;
                    Text inputBoxText = new Text("Input " + zaehl);
                    Button inputButton = new Button("ändern");

                    //on click call modal to set dataType
                    inputButton.setOnAction(new EventHandler<ActionEvent>() {
                        //@Override
                        public void handle(ActionEvent e) {
                            showDataDialog(connection);
                        }
                    });

                    dataInput.getChildren().addAll(inputBoxText, inputButton);
                }
            }
        } else {
            FlowStatusBar.displayError("Node has no connectors! Seems like something went wrong.");
        }

        VBox dataInputBox = new VBox();
        dataInputBox.setSpacing(3);
        dataInputBox.getChildren().addAll(dataInputDesc, dataInput);
        box.getChildren().addAll(dataInputBox);
    }

    /**
     * add dataOutput of element to the property tab
     */
    private static void addDataOutput() {
        dataOutputDesc.setFont(Font.font(null, FontWeight.BOLD, 12));
        dataOutput.getChildren().clear();

        int zaehl = 0;

        //create output fields and set data type
        List<Connector> con = element.getConnectors();
        if (con != null) {
            for (Connector connection : con) {
                if (connection.getConnectorOrientation() == connectorOrientation.right) {

                    //creates text field and output field
                    zaehl++;
                    Text outputBoxText = new Text("Output " + zaehl);
                    Button outputbutton = new Button("ändern");

                    //on click call modal to set dataType
                    outputbutton.setOnAction(new EventHandler<ActionEvent>() {
                        //@Override
                        public void handle(ActionEvent e) {
                            showDataDialog(connection);
                        }
                    });

                    dataOutput.getChildren().addAll(outputBoxText, outputbutton);
                }
            }
        } else {
            FlowStatusBar.displayError("Node has no connectors! Seems like something went wrong.");
        }

        VBox dataOutputBox = new VBox();
        dataOutputBox.setSpacing(3);
        dataOutputBox.getChildren().addAll(dataOutputDesc, dataOutput);
        box.getChildren().addAll(dataOutputBox);
    }

    /**
     * add ability to add left connectors, to the property tab
     */
    private static void addLeftConnectors() {
        leftConnectorsDesc.setFont(Font.font(null, FontWeight.BOLD, 12));

        VBox leftConnectorsBox = new VBox();
        HBox leftConnectorsHBox = new HBox(leftConnectorsAdd, leftConnectorsRemove);
        leftConnectorsHBox.setSpacing(3);
        leftConnectorsBox.setSpacing(3);
        leftConnectorsBox.getChildren().addAll(leftConnectorsDesc, leftConnectorsHBox);
        box.getChildren().addAll(leftConnectorsBox);
    }

    /**
     * add ability to add right connectors, to the property tab
     */
    private static void addRightConnectors() {
        rightConnectorsDesc.setFont(Font.font(null, FontWeight.BOLD, 12));

        VBox rightConnectorsBox = new VBox();
        HBox rightConnectorsHBox = new HBox(rightConnectorsAdd, rightConnectorsRemove);
        rightConnectorsHBox.setSpacing(3);
        rightConnectorsBox.setSpacing(3);
        rightConnectorsBox.getChildren().addAll(rightConnectorsDesc, rightConnectorsHBox);
        box.getChildren().addAll(rightConnectorsBox);
    }

    /**
     * adds the link to the property tab
     */
    private static void addLink() {

        // Clear combobox
        link.getItems().clear();

        // Add null
        link.getItems().add("");

        // set available diagrams
        for (diagram tempDiagram : DataStorage.project.getDiagrams()) {
            // Add all FlowDesign Diagrams
            if (!tempDiagram.getName().equals(DataStorage.activeDiagram.getName()) && tempDiagram.getType() == diagramType.FlowDesign)
                link.getItems().add(tempDiagram.getName());
        }

        link.getSelectionModel().select(element.getLink());

        linkDesc.setFont(Font.font(null, FontWeight.BOLD, 12));

        VBox linkBox = new VBox();
        linkBox.setSpacing(3);
        linkBox.getChildren().addAll(linkDesc, link);
        box.getChildren().addAll(linkBox);
    }

    /**
     * clears the whole property tab
     */
    public static void clearProperties() {
        box.getChildren().clear();
    }

    /**
     * shows the projectsettings in the property tab
     */
    public static void showDiagramSettings() {
        clearProperties();

        //change name
        VBox diagramNameBox = new VBox();
        diagramNameBox.setSpacing(3);
        diagramNameBox.getChildren().addAll(diagramNameDesc, changeDiagramName);

        //clear versions
        version.getItems().clear();

        //set available versions
        for (version ver : DataStorage.activeDiagram.getVersions()) {
            version.getItems().add(ver.getNum());
        }
        version.setValue(DataStorage.activeDiagram.getActiveVersion().getNum());

        //change/add version
        VBox versionBox = new VBox();
        versionBox.setSpacing(3);
        versionBox.getChildren().addAll(versionDesc, version, newVersion);

        //clear parents
        parent.getItems().clear();

        //add null to parents so nothing can be selected
        parent.getItems().add("");
        // set available parents
        for (diagram tempDiagram : DataStorage.project.getDiagrams()) {
            // Add all FlowDesign Diagrams
            if (!tempDiagram.getName().equals(DataStorage.activeDiagram.getName()) && tempDiagram.getType() == diagramType.FlowDesign)
                parent.getItems().add(tempDiagram.getName());
        }
        // Set current parent
        if (!DataStorage.activeDiagram.getParent().equals("root"))
            parent.getSelectionModel().select(DataStorage.activeDiagram.getParent());

        //set up parent vbox
        VBox parentBox = new VBox();
        parentBox.setSpacing(3);
        parentBox.getChildren().addAll(parentDesc, parent);

        box.getChildren().addAll(diagramNameBox, versionBox, parentBox);
    }

    /**
     * updates the property tab based on incoming element
     *
     * @param elmnt Element
     */
    public static void updateProperties(Element elmnt) {

        clearProperties();
        element = elmnt;
        switch (elmnt.getElementType()) {
            case dd_CurvedRedArrow:
                addPosition();
                addSize();
                addLink();
                break;
            case dd_StraightRedArrow:
                addPosition();
                addSize();
                addLabel();
                addAngle();
                addLink();
                break;
            case dd_Image:
                addPosition();
                addSize();
                addZIndexControls();
                break;
            case dd_Text:
                addPosition();
                addSize();
                addText();
                break;
            case dd_Square:
                addPosition();
                addSize();
                addLabel();
                addColor();
                break;
            case fd_Join:
                addPosition();
                addSize();
                addLeftConnectors();
                addRightConnectors();
                addDataInput();
                break;
            case fd_Portal:
                addPosition();
                addSize();
                addLabel();
                addColor();
                addNodeAttribut();
                addDataInput();
                addDataOutput();
                break;
            case fd_ResourceOval:
                addPosition();
                addSize();
                addLabel();
                addColor();
                break;
            case fd_Resource:
                addPosition();
                addSize();
                addLabel();
                addColor();
                break;
            case fd_Split:
                addPosition();
                addSize();
                addDataInput();
                break;
            case fd_StateGet:
                addPosition();
                addSize();
                addLabel();
                addDataInput();
                addDataOutput();
                break;
            case fd_StateSet:
                addPosition();
                addSize();
                addLabel();
                addDataInput();
                addDataOutput();
                break;
            case fd_Unit:
                addPosition();
                addSize();
                addLabel();
                addColor();
                addLink();
                addNodeAttribut();
                addLeftConnectors();
                addRightConnectors();
                addDataInput();
                addDataOutput();
                break;
            case fd_Iteration:
                addPosition();
                addSize();
                addLabel();
                addColor();
                addLink();
                addNodeAttribut();
                addLeftConnectors();
                addRightConnectors();
                addDataInput();
                addDataOutput();
                break;
            case fd_UnitState:
                addPosition();
                addSize();
                addLabel();
                addColor();
                addLeftConnectors();
                addRightConnectors();
                addDataInput();
                addDataOutput();
                break;
            case fd_SourceOfFlow:
                addPosition();
                addSize();
                addColor();
                addDataOutput();
                break;
            case fd_SinkOfFlow:
                addPosition();
                addSize();
                addColor();
                addDataInput();
                break;
            case sud_AdapterSquare:
                addPosition();
                addSize();
                addColor();
                break;
            case sud_AdapterTriangle:
                addPosition();
                addSize();
                addColor();
                break;
            case sud_Database:
                addPosition();
                addSize();
                break;
            case sud_HumanResource:
                addPosition();
                addSize();
                addLabel();
                break;
            case sud_System:
                addPosition();
                addSize();
                addLabel();
                break;
            default:
                showDiagramSettings();
                break;
        }
    }

    /**
     * refreshes the property tab with currently selected element. if no element is selected it will refresh the diagram settings
     */
    public static void refreshProperties(Element element) {
        box.getChildren().clear();
        updateProperties(element);
    }

    public static Text getFirstText() {
        return posXDesc;
    }

    /**
     * shows the datatype dialog based on boolean (true = Input, false = Output)
     *
     * @param con Connector - connector to change
     */
    public static void showDataDialog(Connector con) {
        // Create the custom dialog.
        Dialog dialog = new Dialog();
        dialog.setTitle("Datentyp");
        dialog.setHeaderText("Bitte wählen sie einen Datentyp aus oder fügen sie einen neuen hinzu.");

        //set Image of window
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:frontend/src/resources/icons/icon.png"));

        // Set the button types.
        ButtonType saveButtonType = new ButtonType("Speichern", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create the search and list labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        //init left list
        wrapListAndAddFiltering();

        ListView<String> list = new ListView<>(filteredData);
        list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //create checkbox
        CheckBox cb1 = new CheckBox("wiederholend");

        //create VBox for checkboxes
        VBox cb = new VBox(10, cb1);

        //set right list from saved dataTypes
        ObservableList<HBox> dataTypes = FXCollections.observableArrayList();
        if (!con.getDataType().equals("( )") && !con.getDataType().equals("")) {
            String str = con.getDataType().replaceAll("\\s", "");

            //set checkbox
            if (str.matches(".*\\)\\*{1}")) {
                cb1.setSelected(true);
                //remove * at last character
                str = str.substring(0, str.length() - 1);
            }

            //remove first and last bracket
            str = str.substring(1, str.length() - 1);
            System.out.println(str);
            //split dataTypes
            String[] ary = str.split(",");
            //add every dataType
            for (String data : ary) {
                dataTypes.add(new HBox(new Label(data)));
            }
        }

        ListView<HBox> finalList = new ListView<>(dataTypes);
        finalList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        final ContextMenu contextMenu = new ContextMenu();
        MenuItem edit = new MenuItem("Bearbeiten");
        edit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (!list.getSelectionModel().getSelectedItems().isEmpty()) {
                    // Create the custom dialog.
                    Dialog dialog = new Dialog();
                    dialog.setTitle("Datentyp bearbeiten");
                    dialog.setHeaderText("Bitte geben sie einen Namen und optional eine Beschreibung ihres Datentypes ein.");

                    //set Image of window
                    Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image("file:frontend/src/resources/icons/icon.png"));


                    //create Textfield for name of datatype
                    Text dataname = new Text(list.getSelectionModel().getSelectedItem());
                    //create Textarea for description of datatype
                    TextArea datadesc = new TextArea();
                    datadesc.setText(BackendAPI.getDataTypeDescription(list.getSelectionModel().getSelectedItem()));
                    datadesc.setPrefHeight(400);
                    VBox vBox = new VBox(new Label("Name des Datentypes:"), dataname, new Label("Beschreibung des Datentypes (optional):"), datadesc);
                    vBox.setSpacing(5);

                    //set Textfield and HTMLEditor into pane
                    dialog.getDialogPane().setContent(vBox);

                    // Set the button types.
                    ButtonType saveButtonType = new ButtonType("Speichern", ButtonBar.ButtonData.OK_DONE);
                    dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

                    // Request focus on the search field by default.
                    Platform.runLater(() -> filterInput.requestFocus());

                    // Convert the result to correct notation
                    dialog.setResultConverter(dialogButton -> {
                        if (dialogButton == saveButtonType) {
                            return datadesc.getText();
                        }
                        return null;
                    });

                    Optional<String> result = dialog.showAndWait();

                    result.ifPresent(dataType -> {
                        //add description in backend
                        if (BackendAPI.addDataTypeDescription(list.getSelectionModel().getSelectedItem(), datadesc.getText())) {
                            FlowStatusBar.displaySuccess("Beschreibung wurde geändert.");
                            wrapListAndAddFiltering();
                            list.setItems(filteredData);
                            filterInput.setText(filterInput.getText());
                        } else {
                            FlowStatusBar.displayError("Beschreibung konnte nicht geändert werden.");
                            wrapListAndAddFiltering();
                            list.setItems(filteredData);
                            filterInput.setText(filterInput.getText());
                        }
                    });
                }
            }
        });
        MenuItem delete = new MenuItem("Löschen");
        delete.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (!list.getSelectionModel().getSelectedItems().isEmpty()) {
                    BackendAPI.removeDataType(list.getSelectionModel().getSelectedItem());
                    wrapListAndAddFiltering();
                    list.setItems(filteredData);
                    FlowStatusBar.displaySuccess("Datentyp wurde gelöscht.");
                }
            }
        });
        contextMenu.getItems().addAll(edit, delete);
        list.setContextMenu(contextMenu);

        //disable context menu items when more than one item is selected or when defaultdatatypes are selected
        list.getSelectionModel().getSelectedIndices().addListener(new ListChangeListener<Integer>() {
            @Override
            public void onChanged(Change<? extends Integer> change) {
                if (change.getList().size() >= 2 || DataStorage.defaultDataTypes.contains(list.getSelectionModel().getSelectedItem())) {
                    edit.setDisable(true);
                    delete.setDisable(true);
                } else {
                    edit.setDisable(false);
                    delete.setDisable(false);
                }
            }
        });

        //add handler for doubleclick to switch from left to right list
        list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //double click
                if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton() == MouseButton.PRIMARY) {
                    //switch
                    if (!list.getSelectionModel().getSelectedItems().isEmpty()) {
                        for (String name : list.getSelectionModel().getSelectedItems()) {
                            finalList.getItems().add(new HBox(new Label(name)));
                        }
                    }
                }
            }
        });

        //add handler for doubleclick to remove from right list
        finalList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //double click
                if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton() == MouseButton.PRIMARY) {
                    //switch
                    if (!finalList.getSelectionModel().getSelectedItems().isEmpty()) {
                        finalList.getItems().remove(finalList.getSelectionModel().getSelectedIndex());
                    }
                }
            }
        });

        //add tooltip to dataTypeCells in left list
        list.setCellFactory(param -> new ListCell<String>() {
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(name);
                    if (!BackendAPI.getDataTypeDescription(name).equals("")) {
                        setTooltip(new Tooltip(BackendAPI.getDataTypeDescription(name)));
                    }
                }
            }
        });

        finalList.setCellFactory(param -> new ListCell<HBox>() {

            Label label = new Label("");
            Pane pane = new Pane();
            CheckBox cBox = new CheckBox("mehrfach");

            @Override
            public void updateItem(HBox hBox, boolean empty) {
                super.updateItem(hBox, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Node nodeOut = hBox.getChildren().get(0);
                    if (nodeOut instanceof Label) {
                        String text = ((Label) nodeOut).getText();
                        if (text.charAt(text.length() - 1) == '*') {
                            text = text.substring(0, text.length() - 1);
                            cBox.setSelected(true);
                        }
                        label.setText(text);
                        //set tooltip for dataTypeCell
                        if (!BackendAPI.getDataTypeDescription(text).equals("")) {
                            setTooltip(new Tooltip(BackendAPI.getDataTypeDescription(text)));
                        }
                    }

                    hBox.getChildren().clear();
                    hBox.getChildren().addAll(label, pane, cBox);
                    HBox.setHgrow(pane, Priority.ALWAYS);
                    setGraphic(hBox);
                }
            }
        });

        //adds dataType in backend
        Button addDataType = new Button("Hinzufügen");
        addDataType.setOnAction(new EventHandler<ActionEvent>() {
            //@Override
            public void handle(ActionEvent e) {
                // Create the custom dialog.
                Dialog dialog = new Dialog();
                dialog.setTitle("Datentyp erstellen");
                dialog.setHeaderText("Bitte geben sie einen Namen und optional eine Beschreibung ihres Datentypes ein.");

                //set Image of window
                Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("file:frontend/src/resources/icons/icon.png"));

                //create Textfield for name of datatype
                TextField dataname = new TextField();
                //create HTMLEditor for description of datatype
                TextArea datadesc = new TextArea();
                datadesc.setPrefHeight(400);
                VBox vBox = new VBox(new Label("Name des Datentypes:"), dataname, new Label("Beschreibung des Datentypes (optional):"), datadesc);
                vBox.setSpacing(5);

                //set Textfield and HTMLEditor into pane
                dialog.getDialogPane().setContent(vBox);

                // Set the button types.
                ButtonType saveButtonType = new ButtonType("Speichern", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

                // Request focus on the search field by default.
                Platform.runLater(() -> filterInput.requestFocus());

                // Convert the result to correct notation
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == saveButtonType) {
                        return new Pair<>(dataname.getText(), datadesc.getText());
                    }
                    return null;
                });

                Optional<Pair<String, String>> result = dialog.showAndWait();

                result.ifPresent(dataType -> {
                    if (!dataType.getKey().equals("")) {
                        //add datatype in backend
                        if (BackendAPI.addDataType(dataType.getKey())) {
                            //add description in backend
                            if (BackendAPI.addDataTypeDescription(dataType.getKey(), dataType.getValue())) {
                                FlowStatusBar.displaySuccess("Datentyp und Beschreibung wurde hinzugefügt.");
                                wrapListAndAddFiltering();
                                list.setItems(filteredData);
                                filterInput.setText(filterInput.getText());
                            } else {
                                FlowStatusBar.displaySuccess("Datentyp wurde hinzugefügt.");
                                wrapListAndAddFiltering();
                                list.setItems(filteredData);
                                filterInput.setText(filterInput.getText());
                            }
                        } else {
                            FlowStatusBar.displayError("Fehler beim hinzufügen des Datentypes!");
                        }
                    }
                });
            }
        });

        //switches datatype to the right list
        Button addToList = new Button(" > ");
        addToList.setOnAction(new EventHandler<ActionEvent>() {
            //@Override
            public void handle(ActionEvent e) {
                if (!list.getSelectionModel().getSelectedItems().isEmpty()) {
                    for (String name : list.getSelectionModel().getSelectedItems()) {
                        finalList.getItems().add(new HBox(new Label(name)));
                    }
                }
            }
        });

        //switches datatype back from the right list
        Button removeFromList = new Button(" < ");
        removeFromList.setOnAction(new EventHandler<ActionEvent>() {
            //@Override
            public void handle(ActionEvent e) {
                if (!finalList.getSelectionModel().getSelectedItems().isEmpty()) {
                    ObservableList<Integer> reversedList = FXCollections.observableArrayList(finalList.getSelectionModel().getSelectedIndices());
                    FXCollections.reverse(reversedList);
                    for (int i : reversedList) {
                        finalList.getItems().remove(i);
                    }
                }
            }
        });

        //align buttons
        VBox vButtons = new VBox(10, addToList, removeFromList);
        vButtons.setAlignment(Pos.CENTER);

        //align searchbox and addDataType button
        Pane pane = new Pane();
        HBox hBox = new HBox(filterInput, pane, addDataType);
        HBox.setHgrow(pane, Priority.ALWAYS);

        grid.add(new Label("Suche:"), 0, 1);
        grid.add(hBox, 1, 1);
        grid.add(new Label("Datentypen:"), 1, 0);
        grid.add(new Label("Auswahl:"), 3, 1);
        grid.add(list, 1, 2);
        grid.add(vButtons, 2, 2);
        grid.add(finalList, 3, 2);
        grid.add(cb, 4, 2);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the search field by default.
        Platform.runLater(() -> filterInput.requestFocus());

        // Convert the result to correct notation
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                if (finalList.getItems().isEmpty()) {
                    return "( )";
                } else {
                    ObservableList<HBox> hboxList = finalList.getItems();
                    StringBuilder sb = new StringBuilder("(");
                    for (HBox hbox : hboxList) {
                        Node nodeLb = hbox.getChildren().get(0);
                        Node nodeCB = hbox.getChildren().get(2);
                        if (nodeLb instanceof Label) {
                            if (nodeCB instanceof CheckBox) {
                                if (((CheckBox) nodeCB).isSelected()) {
                                    sb.append(' ' + ((Label) nodeLb).getText() + '*' + ',');
                                } else {
                                    sb.append(' ' + ((Label) nodeLb).getText() + ',');
                                }
                            }
                        }
                    }
                    //delete space after first (
                    sb.deleteCharAt(1);
                    sb.setCharAt(sb.length() - 1, ')');
                    if (cb1.isSelected()) {
                        sb.append('*');
                    }
                    return sb.toString();
                }
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(dataType -> {
            con.setDataType(dataType);
        });
    }

    /**
     * refreshes FilteredList
     */
    private static void wrapListAndAddFiltering() {
        filteredData = new FilteredList<>(FXCollections.observableArrayList(DataStorage.dataTypes), s -> true);
        filterInput.textProperty().addListener(obs -> {
            String filter = filterInput.getText();
            if (filter == null || filter.length() == 0) {
                filteredData.setPredicate(s -> true);
            } else {
                filteredData.setPredicate(s -> s.toLowerCase().contains(filter.toLowerCase()));
            }
        });
    }
}
