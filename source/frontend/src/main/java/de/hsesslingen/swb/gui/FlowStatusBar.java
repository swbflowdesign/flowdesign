package de.hsesslingen.swb.gui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Timer;
import java.util.TimerTask;


public class FlowStatusBar {

    public static HBox statusbar;
    public static Timer timer = new Timer();


    /**
     * Returns the statusbar
     *
     * @return statusbar
     */
    public static HBox getStatusBar() {
        statusbar = new HBox();
        statusbar.setMinHeight(30);
        statusbar.setAlignment(Pos.CENTER_LEFT);
        return statusbar;
    }

    /**
     * Clears the statusbar
     */
    public static void clear() {
        statusbar.getChildren().clear();
        statusbar.setStyle("");
    }

    /**
     * Updates the statusbar to display a success message
     *
     * @param msg Message
     */
    public static void displaySuccess(String msg) {
        Text text = new Text(msg);
        text.setFill(Color.WHITE);
        text.setFont(Font.font(null, FontWeight.BOLD, 13));
        statusbar.getChildren().clear();
        statusbar.getChildren().add(text);
        statusbar.setStyle("-fx-background-color:#52B465;");

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        clear();
                    }
                });
            }
        }, 4000);
    }

    /**
     * Updates the statusbar to display a warning message
     *
     * @param msg Message
     */
    public static void displayWarning(String msg) {
        Text text = new Text(msg);
        text.setFill(Color.WHITE);
        text.setFont(Font.font(null, FontWeight.BOLD, 13));
        statusbar.getChildren().clear();
        statusbar.getChildren().add(text);
        statusbar.setStyle("-fx-background-color:#F5DDA4;");

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        clear();
                    }
                });
            }
        }, 4000);
    }

    /**
     * Updates the statusbar to display an error message
     *
     * @param msg Message
     */
    public static void displayError(String msg) {
        Text text = new Text(msg);
        text.setFill(Color.WHITE);
        text.setFont(Font.font(null, FontWeight.BOLD, 13));
        statusbar.getChildren().clear();
        statusbar.getChildren().add(text);
        statusbar.setStyle("-fx-background-color:#E2494E;");

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        clear();
                    }
                });
            }
        }, 4000);
    }
}
