package de.hsesslingen.swb.gui;

import de.hsesslingen.swb.BackendAPI;
import de.hsesslingen.swb.DataStorage;
import de.hsesslingen.swb.DiagramAPI;
import de.hsesslingen.swb.diagram.enums.diagramType;
import de.hsesslingen.swb.diagram.enums.nodeType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.List;


public class Components {

    private static Tab componentsTab = new Tab();
    private static Accordion accordion;

    /**
     * Fills the componentTab with Icons based on diagram type
     *
     * @param typeOfThisDiagram
     */
    public static void setComponents(diagramType typeOfThisDiagram) {

        componentsTab.setText("Komponenten");
        componentsTab.setClosable(false);


        accordion = new Accordion();

        /* Basic Forms Tab */

        TitledPane tp1 = new TitledPane();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); //disable horizontal Scrollbar

        GridPane grid = new GridPane();
        grid.setHgap(10); //horizontal gap in pixels
        grid.setVgap(5); //vertical gap in pixels
        grid.setPadding(new Insets(0, 10, 10, 10)); //margins around the whole grid

        /* Add images and text */
        //List of the elements with icons
        List<String> enumList = DiagramAPI.getEnumTypes(typeOfThisDiagram);
        for (int u = 0; u < enumList.size(); u++) {
            if (enumList.get(u).equals(nodeType.dd_Image.toString())) {
                enumList.remove(u);
            }
        }
        //Add all the icons with their names to the pane
        for (int i = 0; i < enumList.size(); i++) {
            // If it is an image continue (Images are added in an extra accordion pane)
            /*
            if(enumList.get(i).equals(nodeType.dd_Image.toString()))

                continue;
            */

            StackPane pane = new StackPane();
            pane.setMinHeight(80);
            Image bild = new Image("file:frontend/src/resources/components/" + typeOfThisDiagram.toString() + "Icons/" + enumList.get(i) + ".png");
            Text iconName = new Text(enumList.get(i).replace("fd_", "").replace("dd_", "").replace("sud_", ""));
            ImageView pic = new ImageView();
            pic.setFitWidth(100);
            pic.setPreserveRatio(true);
            pic.setSmooth(true);
            pic.setImage(bild);
            pane.getChildren().add(pic);
            pane.getChildren().add(iconName);
            pane.setAlignment(Pos.BASELINE_CENTER);
            pane.setId(enumList.get(i));
            if (i % 2 == 0) {
                grid.add(pane, 0, i / 2);
            } else {
                grid.add(pane, 1, i / 2);
            }

            //Eventhandler for draging component onto graph
            pane.setOnDragDetected(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    /* drag was detected, start a drag-and-drop gesture*/
                    Dragboard db = pane.startDragAndDrop(TransferMode.COPY);
                    db.setDragView(bild, 100, 100);   /*seting the drag image and offset it by 100*/
                    ClipboardContent content = new ClipboardContent();
                    content.putString(pane.getId()); /*set the nodeType of the component as string of dragboard*/
                    db.setContent(content);
                    event.consume();
                }
            });
        }

        scrollPane.setContent(grid); //the Grid is now inside the Pane with a scrollbar

        tp1.setText("Basic Forms");
        tp1.setContent(scrollPane); //the Pane is now the content of the Tab (Basic forms)

        tp1.setAnimated(false);
        accordion.getPanes().add(tp1); //Adding the Basic forms Tab to the accordion
        accordion.setExpandedPane(tp1);

        /* Basic Forms Tab End */


        /* Images Tab Start */

        if (typeOfThisDiagram == diagramType.DialogDiagram) {

            TitledPane tp2 = new TitledPane();


            ScrollPane scrollPaneImages = new ScrollPane();
            scrollPaneImages.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); //disable horizontal Scrollbar

            GridPane gridImages = new GridPane();
            gridImages.setHgap(10); //horizontal gap in pixels
            gridImages.setVgap(10); //vertical gap in pixels
            gridImages.setPadding(new Insets(10, 10, 10, 10)); //margins around the whole grid

            /* Add import button */
            StackPane importImagesPane = new StackPane();
            importImagesPane.setPrefWidth(200);
            importImagesPane.setPrefHeight(50);
            importImagesPane.setAlignment(Pos.CENTER);
            Button importImageBtn = new Button("Bild importieren");
            importImageBtn.setOnAction(new EventHandler<ActionEvent>() {
                //@Override
                public void handle(ActionEvent e) {
                    Actions.importImageToResources();
                    /* Update GUI */
                    RightSideBar.setRightContainer(DataStorage.activeDiagram.getName());
                    accordion.setExpandedPane(accordion.getPanes().get(1));
                }
            });
            importImagesPane.getChildren().add(importImageBtn);
            gridImages.add(importImagesPane, 0, 0, 2, 1);

            /* Add images and text */
            List<String> images = BackendAPI.file().getAllImagesFromResources();
            if (images != null) {

                for (int i = 0; i < images.size(); i++) {
                    StackPane pane = new StackPane();
                    pane.setMinHeight(80);
                    String imageName = images.get(i);
                    Image bild = BackendAPI.file().getImageFromResources(imageName);
                    // Text iconName = new Text(diagramAPI.getEnumTypes(typeOfThisDiagram).get(i).replace("fd_", "").replace("dd_", "").replace("sud_", ""));
                    ImageView pic = new ImageView();
                    pic.setFitWidth(100);
                    pic.setPreserveRatio(true);
                    pic.setSmooth(true);
                    pic.setImage(bild);
                    pane.getChildren().add(pic);
                    // pane.getChildren().add(iconName);
                    pane.setAlignment(Pos.BASELINE_CENTER);
                    pane.setId(nodeType.dd_Image.toString());
                    if (i % 2 == 0) {
                        gridImages.add(pane, 0, (i / 2) + 1);
                    } else {
                        gridImages.add(pane, 1, (i / 2) + 1);
                    }

                    // Eventhandler for draging component onto graph
                    pane.setOnDragDetected(new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent event) {
                            /* drag was detected, start a drag-and-drop gesture*/
                            Dragboard db = pane.startDragAndDrop(TransferMode.COPY);
                            db.setDragView(bild, 100, 100);   // seting the drag image and offset it by 100
                            ClipboardContent content = new ClipboardContent();
                            content.putString(pane.getId()); // set the nodeType of the component as string of dragboard
                            content.putRtf(imageName); // set the name of the image as URL of dragborad
                            db.setContent(content);
                            event.consume();
                        }
                    });
                }

            }


            scrollPaneImages.setContent(gridImages); //the Grid is now inside the Pane with a scrollbar

            tp2.setContent(scrollPaneImages); //the Pane is now the content of the Tab (Basic forms)


            tp2.setText("Bilder");
            tp2.setAnimated(false);
            accordion.getPanes().add(tp2); // make it so the Basic Forms Tab is always auto extended

        }

        /* Images Tab End */


        componentsTab.setContent(accordion); //the accordion is now the content of the Components Tab

    }

    /**
     * Returns the current ComponentTab
     *
     * @return Tab
     */
    public static Tab getComponentsTab() {

        return componentsTab;
    }

    /**
     * Removes everything inside of the Components Tab
     */
    public static void clearComponents() {

        // componentsTab.getTabPane().getTabs().clear();
    }
}
