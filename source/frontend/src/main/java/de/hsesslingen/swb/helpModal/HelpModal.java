package de.hsesslingen.swb.helpModal;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HelpModal {

    private final ObservableList<ShortcutListItem> data =
            FXCollections.observableArrayList(
                    new ShortcutListItem("Strg + S", "Projekt speichern"),
                    new ShortcutListItem("Strg + N", "Ist ein Diagramm geöffnet, wird ein neues Diagramm der selben Diagrammart erstellt."),
                    new ShortcutListItem("Strg + Entf", "Ist ein Element auf der Zeichenfläche markiert, wird dieses gelöscht."),
                    new ShortcutListItem("Strg + U", "Ist ein Element auf der Zeichenfläche markiert, wird ein neues Unit Element an diese angefügt."),
                    new ShortcutListItem("Strg + P", "Ist ein Element auf der Zeichenfläche markiert, wird ein neues Portal Element an diese angefügt."),
                    new ShortcutListItem("Strg + I", "Ist ein Element auf der Zeichenfläche markiert, wird ein neues Iterator Element an diese angefügt."),
                    new ShortcutListItem("Strg + J", "Ist ein Element auf der Zeichenfläche markiert, wird ein neues Join Element an diese angefügt."),
                    new ShortcutListItem("Strg + R", "Ist ein Element auf der Zeichenfläche markiert, wird ein neues Resource Element an diese angefügt."),
                    new ShortcutListItem("Strg + UP", "Navigiere auf der Zeichenfläche zu dem darüberligenden Element."),
                    new ShortcutListItem("Strg + DOWN", "Navigiere auf der Zeichenfläche zu dem darunterliegneden Element."),
                    new ShortcutListItem("Strg + LEFT", "Navigiere auf der Zeichenfläche zu dem nächsten Element auf der linken Seite."),
                    new ShortcutListItem("Strg + RIGHT", "Navigiere auf der Zeichenfläche zu dem nächsten Element auf der rechten Seite."),
                    new ShortcutListItem("Strg + ENTER", "Aktiviere bzw. deaktiviere das Textfeld.")
            );

    public HelpModal() {

        /* Add TabelView */
        TableView table = new TableView();
        table.setEditable(false);


        /* Add columns */
        TableColumn shortcutCol = new TableColumn("Kombination");
        shortcutCol.setCellValueFactory(
                new PropertyValueFactory<ShortcutListItem, String>("shortcut")
        );

        TableColumn descriptionCol = new TableColumn("Beschreibung");
        descriptionCol.setCellValueFactory(
                new PropertyValueFactory<ShortcutListItem, String>("description")
        );

        table.getColumns().addAll(shortcutCol, descriptionCol);


        /* Add data */
        table.setItems(data);


        /* Setup scene and stage */
        Scene modalScene = new Scene(table, 631, 345);
        Stage modalStage = new Stage();
        modalStage.setScene(modalScene);
        modalStage.initModality(Modality.APPLICATION_MODAL);

        /* Set title */
        modalStage.setTitle("Flow Designer - Tastenkombinationen");

        /* Set the Icon of the stage */
        modalStage.getIcons().add(new Image("file:frontend/src/resources/icons/icon.png"));

        /* Show modal */
        modalStage.showAndWait();

    }

    protected class ShortcutListItem {

        private final SimpleStringProperty shortcut;
        private final SimpleStringProperty description;

        public ShortcutListItem(String shortcut, String description) {
            this.shortcut = new SimpleStringProperty(shortcut);
            this.description = new SimpleStringProperty(description);
        }

        public String getShortcut() {
            return shortcut.get();
        }

        public void setShortcut(String shortcut) {
            this.shortcut.set(shortcut);
        }

        public SimpleStringProperty shortcutProperty() {
            return shortcut;
        }

        public String getDescription() {
            return description.get();
        }

        public void setDescription(String description) {
            this.description.set(description);
        }

        public SimpleStringProperty descriptionProperty() {
            return description;
        }
    }

}
