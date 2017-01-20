package de.hsesslingen.swb.gui.canvas;

import de.hsesslingen.swb.App;
import de.hsesslingen.swb.gui.FlowMainContent;
import de.hsesslingen.swb.gui.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;


public class Fullscreen {

    public static Scene canvasFullscreen;
    /**
     * Handle ESC click event for leaving fullscreen
     */
    public static EventHandler escapeClickHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent t) {
            if (t.getCode() == KeyCode.ESCAPE) {
                canvasFullscreenHide();
            }
        }
    };

    /**
     * Show the TabPane in fullscreen
     */
    public static void canvasFullscreenShow() {

        Main.root.setCenter(null);

        Button fullscreenHideBtn = new Button("Fullscreen verlassen");
        fullscreenHideBtn.setStyle("-fx-padding: 20px");
        fullscreenHideBtn.setOnAction(new EventHandler<ActionEvent>() {
            //@Override
            public void handle(ActionEvent e) {
                canvasFullscreenHide();
            }
        });

        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.BOTTOM_RIGHT);
        stackPane.getChildren().addAll(FlowMainContent.mainContent, fullscreenHideBtn);

        FlowMainContent.mainContent.setLayoutX(0);
        FlowMainContent.mainContent.setLayoutY(0);

        canvasFullscreen = new Scene(stackPane);

        App.setScene(canvasFullscreen);
        App.setFullscreen(true);

        App.appStage.addEventHandler(KeyEvent.KEY_PRESSED, escapeClickHandler);

    }

    /**
     * Hide the TabPane from fullscreen
     */
    public static void canvasFullscreenHide() {

        App.appStage.removeEventHandler(KeyEvent.KEY_PRESSED, escapeClickHandler);

        canvasFullscreen = null;

        Main.root.setCenter(FlowMainContent.mainContent);
        App.setScene(Main.getMainScene());

        // Center stage
        App.setFullscreen(false);
        App.centerStage();

    }

}
