package de.hsesslingen.swb.gui;

import de.hsesslingen.swb.BackendAPI;
import de.hsesslingen.swb.DataStorage;
import de.hsesslingen.swb.diagram.enums.diagramType;
import de.hsesslingen.swb.project.ProjectTreeItem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.List;


public class FlowFileTree {

    private static VBox filetree = new VBox();
    private static Button SUDButton = new Button("+");
    private static Button DDButton = new Button("+");
    private static Button FDButton = new Button("+");
    private static TreeItem<CustomItem> rootItem = new TreeItem<>(new CustomItem(new ImageView(new Image("file:frontend/src/resources/icons/folder.png")), new Label("Kein Projekt geöffnet")));
    private static TreeItem<CustomItem> SUD = new TreeItem<>(new CustomItem(new ImageView(new Image("file:frontend/src/resources/icons/folder.png")), new Label("System Umwelt Diagramm"), SUDButton, diagramType.SystemUmweltDiagram));
    private static TreeItem<CustomItem> DD = new TreeItem<>(new CustomItem(new ImageView(new Image("file:frontend/src/resources/icons/folder.png")), new Label("Dialog Diagramm"), DDButton, diagramType.DialogDiagram));
    private static TreeItem<CustomItem> FD = new TreeItem<>(new CustomItem(new ImageView(new Image("file:frontend/src/resources/icons/folder.png")), new Label("Flow Design Diagramm"), FDButton, diagramType.FlowDesign));
    private static TreeView<CustomItem> tree = new TreeView<>();
    private static ContextMenu contextMenu = new ContextMenu();

    public static VBox getFileTree() {
        return filetree;
    }

    public static void init() {
        rootItem.getChildren().addAll(SUD, DD, FD);
        rootItem.setExpanded(true);
        tree.prefHeightProperty().bind(filetree.heightProperty());
        tree.setContextMenu(contextMenu);
        tree.setRoot(rootItem);
        filetree.getChildren().add(tree);


        //set Handler for double click or right click on tree item
        tree.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //double click
                if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton() == MouseButton.PRIMARY) {
                    //open diagram if not rootitem of filetree or child of rootitem
                    if (tree.getSelectionModel().getSelectedItem() != null) {
                        if (tree.getSelectionModel().getSelectedItem() != rootItem && tree.getSelectionModel().getSelectedItem().getParent() != rootItem) {
                            // Open diagram
                            Actions.openDiagram(tree.getSelectionModel().getSelectedItem().getValue().getLabel());
                        }
                    }
                }

                //right click
                if (mouseEvent.getButton() == MouseButton.SECONDARY && tree.getSelectionModel().getSelectedItem() != null) {
                    //update contextmenu and show it afterwards
                    updateContextmenu(tree.getSelectionModel().getSelectedItem());
                    contextMenu.show(tree, 0, 0);

                }
            }
        });

        //style buttons
        FDButton.setStyle("-fx-background-radius: 20;-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 14px;-fx-padding: 0 10;");
        DDButton.setStyle("-fx-background-radius: 20;-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 14px;-fx-padding: 0 10;");
        SUDButton.setStyle("-fx-background-radius: 20;-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 14px;-fx-padding: 0 10;");
        FDButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        FDButton.setCursor(Cursor.HAND);
                    }
                });
        DDButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        DDButton.setCursor(Cursor.HAND);
                    }
                });
        SUDButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        SUDButton.setCursor(Cursor.HAND);
                    }
                });

        //on click create FD diagram
        FDButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Actions.createDiagram(diagramType.FlowDesign, "", null);
            }
        });

        //on click create DD diagram
        DDButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Actions.createDiagram(diagramType.DialogDiagram, "", null);
            }
        });

        //on click create SUD diagram
        SUDButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Actions.createDiagram(diagramType.SystemUmweltDiagram, "", null);
            }
        });
    }

    /**
     * Updates the filetree with the current project loaded in backend
     */
    public static void updateFileTree() {

        SUD.getChildren().clear();
        DD.getChildren().clear();
        FD.getChildren().clear();

        //show buttons to create new diagram
        SUDButton.setVisible(true);
        DDButton.setVisible(true);
        FDButton.setVisible(true);

        // Set project name as root item
        rootItem.getValue().setLabel(DataStorage.project.getName());

        List<ProjectTreeItem> list = BackendAPI.project().getProjectTree();

        for (int i = 0; i < list.size(); i++) {

            TreeItem<CustomItem> item = new TreeItem<>(new CustomItem(new Label(list.get(i).getName()), list.get(i).getType()));

            if (list.get(i).getType() == diagramType.SystemUmweltDiagram) {
                SUD.getChildren().add(item);
            } else if (list.get(i).getType() == diagramType.DialogDiagram) {
                DD.getChildren().add(item);
            } else if (list.get(i).getType() == diagramType.FlowDesign) {

                FD.getChildren().add(item);

                if (list.get(i).getChildren().size() > 0) {

                    addTreeItemRec(list.get(i).getChildren(), item);

                }
            }
        }
    }

    private static void addTreeItemRec(List<ProjectTreeItem> block, TreeItem<CustomItem> item) {

        for (int i = 0; i < block.size(); i++) {

            TreeItem<CustomItem> tempItem = new TreeItem<>(new CustomItem(new Label(block.get(i).getName()), block.get(i).getType()));

            item.getChildren().add(tempItem);

            if (block.get(i).getChildren().size() > 0) {

                addTreeItemRec(block.get(i).getChildren(), tempItem);

            }
        }
    }


    //updates the Contextmenu to display right menuitems
    private static void updateContextmenu(TreeItem<CustomItem> item) {

        contextMenu.getItems().clear();
        if (item == rootItem) {
            if (item.getValue().getLabel().equals("Kein Projekt geöffnet")) {
                MenuItem openProject = new MenuItem("Projekt öffnen");
                openProject.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        Actions.openProject();
                    }
                });
                contextMenu.getItems().addAll(openProject);
            } else {
                MenuItem closeProject = new MenuItem("Projekt schließen");
                closeProject.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        Actions.closeProject();
                    }
                });
                MenuItem deleteProject = new MenuItem("Projekt löschen");
                deleteProject.setDisable(true);
                deleteProject.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        Actions.deleteProject();
                    }
                });
                contextMenu.getItems().addAll(closeProject, deleteProject);
            }
        } else if (item.getParent() == rootItem) {
            if (!item.getParent().getValue().getLabel().equals("Kein Projekt geöffnet")) {
                MenuItem newDiagram = new MenuItem("Neues Diagramm");
                newDiagram.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        Actions.createDiagram(item.getValue().getType(), "", null);
                    }
                });
                contextMenu.getItems().addAll(newDiagram);
            }
        } else {
            MenuItem delete = new MenuItem("Löschen");
            delete.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    Actions.deleteDiagram(item.getValue().getLabel());
                }
            });

            MenuItem rename = new MenuItem("Umbenennen");
            rename.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    Actions.renameDiagram(item.getValue().getLabel());
                }
            });
            contextMenu.getItems().addAll(delete, rename);
        }
    }


    public static void destroyFileTree() {
        rootItem.getValue().setLabel("Kein Projekt geöffnet");

        //hide buttons to create new diagram and clear files contained in folders
        SUDButton.setVisible(false);
        DDButton.setVisible(false);
        FDButton.setVisible(false);
        SUD.getChildren().clear();
        DD.getChildren().clear();
        FD.getChildren().clear();
    }

    public static void updateSelectedItem(String nameOfDiagram) {

        //TODO: think of an algorithm for for x subLayers of a diagram (currently x = 2)
        List<TreeItem<CustomItem>> diagramTypes = tree.getRoot().getChildren();
        for (int i = 0; i < diagramTypes.size(); i++) {
            List<TreeItem<CustomItem>> diagrams = tree.getRoot().getChildren().get(i).getChildren();
            for (int u = 0; u < diagrams.size(); u++) {
                TreeItem<CustomItem> diagram = tree.getRoot().getChildren().get(i).getChildren().get(u);
                if (diagram.getValue().getLabel() == nameOfDiagram) {
                    tree.getSelectionModel().select(diagram);
                } else if (diagram.getChildren().size() > 0) {
                    //repeat top (algorithm)
                    tree.getRoot().getChildren().get(i).setExpanded(true);
                    List<TreeItem<CustomItem>> diag = tree.getRoot().getChildren().get(i).getChildren().get(u).getChildren();
                    for (int z = 0; z < diag.size(); z++) {
                        TreeItem<CustomItem> dia = tree.getRoot().getChildren().get(i).getChildren().get(u).getChildren().get(z);
                        if (dia.getValue().getLabel() == nameOfDiagram) {
                            tree.getSelectionModel().select(dia);
                        } else if (dia.getChildren().size() > 0) {
                            //repeat top (algorithm)
                            diagram.setExpanded(true);
                            List<TreeItem<CustomItem>> di = tree.getRoot().getChildren().get(i).getChildren().get(u).getChildren().get(z).getChildren();
                            for (int t = 0; t < di.size(); t++) {
                                TreeItem<CustomItem> d = tree.getRoot().getChildren().get(i).getChildren().get(u).getChildren().get(z).getChildren().get(t);
                                if (d.getValue().getLabel() == nameOfDiagram) {
                                    tree.getSelectionModel().select(d);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
