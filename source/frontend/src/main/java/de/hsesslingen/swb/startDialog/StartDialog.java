package de.hsesslingen.swb.startDialog;

import de.hsesslingen.swb.App;
import de.hsesslingen.swb.BackendAPI;
import javafx.scene.Scene;


public class StartDialog {

    private static Scene dialogWindowOne;
    private static Scene dialogWindowTwo;

    public static void launch() {

        /* initialize datastorage */
        BackendAPI.initDataStorage();

        /* get dialog window one */
        dialogWindowOne = DialogWindowOne.getScene();
        dialogWindowOne.getStylesheets().add("file:frontend/src/resources/stylesheets/DialogWindowOne.css");

        /* get dialog window two */
        dialogWindowTwo = DialogWindowTwo.getScene();
        dialogWindowTwo.getStylesheets().add("file:frontend/src/resources/stylesheets/DialogWindowTwo.css");


        /* set handler for the scene switch buttons */
        DialogWindowOne.newProjectBtn.setOnAction(e -> App.setScene(dialogWindowTwo));
        DialogWindowTwo.abortBtn.setOnAction(e -> App.setScene(dialogWindowOne));

    }

    public static Scene getStartDialog() {
        return dialogWindowOne;
    }

}

