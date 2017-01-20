package de.hsesslingen.swb;

import de.hsesslingen.swb.gui.Main;
import de.hsesslingen.swb.gui.shortcuts;
import de.hsesslingen.swb.startDialog.StartDialog;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App extends Application {

    public static Stage appStage;

    public static void main(String[] args) {
        Application.launch(args);
    }

    public static void openMain() {

        // Hide DialogWindow and show Main
        Main.launch();
        setScene(Main.getMainScene());

        // Center stage
        centerStage();
        setResizable(true);
        appStage.setMaximized(true);


        /* Save project on window close */
        appStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (!BackendAPI.project().closeProject()) {
                    we.consume();
                } else {
                    System.exit(0);
                }
            }
        });


        /* Adding keyboard shortcuts */
        shortcuts.init();

    }

    public static void setScene(Scene scene) {
        appStage.setScene(scene);
    }

    public static void setResizable(Boolean bool) {
        appStage.setResizable(bool);
    }

    public static void setFullscreen(Boolean bool) {
        appStage.setFullScreen(bool);
        if (!bool) {
            appStage.setMaximized(false);
            appStage.setMaximized(true);
        }
    }

    public static void centerStage() {

        // Center stage on screen
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        appStage.setX((primScreenBounds.getWidth() - appStage.getWidth()) / 2);
        appStage.setY((primScreenBounds.getHeight() - appStage.getHeight()) / 2);

    }

    @Override
    public void start(Stage stage) {

        appStage = stage;

        // Set the title of the Stage
        stage.setTitle("Flow Designer");

        // Set the Icon of the stage
        stage.getIcons().add(new Image("file:frontend/src/resources/icons/icon.png"));


        // Show StartDialog
        StartDialog.launch();
        setScene(StartDialog.getStartDialog());
        setResizable(false);

        stage.show();

    }

}
