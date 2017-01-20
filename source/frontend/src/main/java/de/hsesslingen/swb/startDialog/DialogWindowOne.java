package de.hsesslingen.swb.startDialog;

import de.hsesslingen.swb.App;
import de.hsesslingen.swb.BackendAPI;
import de.hsesslingen.swb.Storage.LastProject;
import de.hsesslingen.swb.fileSystem.fileChooser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;


public class DialogWindowOne {

    public static Scene scene;
    public static Button newProjectBtn;


    /**
     * Creates the first DialogWindow scene and fills it with all of it's components (except the handler for the newProjectBtn)
     *
     * @return Scene
     */
    public static Scene getScene() {
        /* Create a BorderPane */
        BorderPane root = new BorderPane();
        /* Left */
        VBox left = new VBox();

        left.setStyle("-fx-background-color: deepskyblue");
        left.setPrefSize(300, 440);
        /* Right */
        VBox right = new VBox();
        right.setPrefSize(400, 440);
        right.setAlignment(Pos.CENTER);
        right.setSpacing(20);
        /* Bottom */
        HBox bottom = new HBox();
        bottom.setStyle("-fx-background-color: #585858");
        bottom.setPrefSize(700, 50);
        bottom.setSpacing(275);
        bottom.setPadding(new Insets(12.5, 0, 0, 100));

        root.setLeft(left);
        root.setCenter(right);
        root.setBottom(bottom);

        // Set the Size of Borderpane
        root.setPrefSize(700, 440);


        /* Flow Designer Logo */
        Image logo = new Image("file:frontend/src/resources/icons/icon.png");
        ImageView ivLogo = new ImageView();
        ivLogo.setImage(logo);
        right.getChildren().add(ivLogo);


        /* Recently Used Projekts */
        List<LastProject> recentlyUsedProjects = BackendAPI.project().getLastProjects();
        /* set Number of Elements */
        int numberOfLinesInFiles = recentlyUsedProjects.size();
        GridPane grid = new GridPane();
        grid.setVgap(numberOfLinesInFiles + 1);
        /* Headline */
        Text text = new Text();
        HBox UeberschriftText = new HBox();
        text.setText(" Zuletzt verwendete Projekte:");
        text.setFill(Color.web("#585858"));
        text.setStyle("-fx-font-size: 17; -fx-font-weight: bold;");
        UeberschriftText.setPadding(new Insets(0, 0, 10, 5));
        UeberschriftText.getChildren().add(text);

        grid.add(UeberschriftText, 0, 0);

        /* Buttons for the last used projects */
        DialogWindowOne.setLastUsedProjectsInGrid(numberOfLinesInFiles, recentlyUsedProjects, grid);


        /* Styling for the LastUsedProjects area */
        grid.setStyle("-fx-background-color: deepskyblue; -fx-font-family: Raleway;");
        grid.setPadding(new Insets(10, 0, 10, 0));
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); //disable horizontal Scrollbar
        scrollPane.setContent(grid);
        scrollPane.setPrefSize(300, 440);
        scrollPane.setStyle("-fx-background: deepskyblue; -fx-padding: 0; -fx-focus-color: transparent;");
        left.getChildren().add(scrollPane);


        // Add 'Neues Projekt' button
        newProjectBtn = new Button("Neues Projekt");
        bottom.getChildren().add(newProjectBtn);


        // Add 'Projekt öffnen' button
        Button openProjectBtn = new Button("Projekt öffnen");
        openProjectBtn.setOnAction(new EventHandler<ActionEvent>() {
            //@Override
            public void handle(ActionEvent e) {

                String[] extensions = new String[]{"flowproject"};
                File file = fileChooser.showOpenDialog(extensions);
                System.out.println(file);

                if (file != null) {
                    BackendAPI.project().openProject(file.getPath());

                    // Show Main Window
                    App.openMain();
                }

            }
        });
        openProjectBtn.setPrefSize(100, 25);
        bottom.getChildren().add(openProjectBtn);


        // Create the Scene = First aperance of the Dialog window
        scene = new Scene(root);

        return scene;
    }

    private static void setLastUsedProjectsInGrid(int numberOfLinesInFiles, List<LastProject> recentlyUsedProjects, GridPane grid) {

        VBox recentlyUsedProjectsBtn = new VBox();
        for (int i = 0; i < numberOfLinesInFiles; i++) {
            recentlyUsedProjectsBtn = new VBox();
            recentlyUsedProjectsBtn.setId("recentlyUsedProjectsBtn");


            /* first line in the Button = name of the last used project */
            HBox projectnamebox = new HBox();
            Text projectname = new Text(recentlyUsedProjects.get(i).getName());
            projectnamebox.getChildren().add(projectname);
            recentlyUsedProjectsBtn.getChildren().add(projectnamebox);

            /* second line of the Button = path to the last used project */
            String fullProjectPath = recentlyUsedProjects.get(i).getFilePath(); //get the path
            String partsOfProjectPath[] = fullProjectPath.split(Pattern.quote(File.separator));    //split the path at \
            String finalPartsOfProjectPath[] = new String[partsOfProjectPath.length];    //String Array for the parts of the path after it was transformed

            /* if a part of the path is longer than 9 it gets shortened */
            for (int u = 3; u < partsOfProjectPath.length; u++) {

                String partOfProjectPathIsToBig = partsOfProjectPath[u];
                /*
                if (partsOfProjectPath[u].length() > 9) {
                    finalPartsOfProjectPath[u] = "" + partOfProjectPathIsToBig.charAt(0) + partOfProjectPathIsToBig.charAt(1) + partOfProjectPathIsToBig.charAt(2) + "..."
                            + partOfProjectPathIsToBig.charAt(partOfProjectPathIsToBig.length() - 3) + partOfProjectPathIsToBig.charAt(partOfProjectPathIsToBig.length() - 2) + partOfProjectPathIsToBig.charAt(partOfProjectPathIsToBig.length() - 1);

                } else
                */
                finalPartsOfProjectPath[u] = new String(partOfProjectPathIsToBig);
            }
            /* if the path leads to a folder in the users home directory the beginning gets substituted for a ~ */
            String firstThreePartsOfPath = partsOfProjectPath[0] + File.separator + partsOfProjectPath[1] + File.separator + partsOfProjectPath[2];
            String displayableProjectPath;
            String homePath = System.getProperty("user.home").toString();
            if (firstThreePartsOfPath.equals(homePath)) {
                displayableProjectPath = "~";
                for (int v = 3; v < partsOfProjectPath.length; v++) {
                    displayableProjectPath += File.separator + finalPartsOfProjectPath[v];
                }
            } else {
                displayableProjectPath = firstThreePartsOfPath;
                for (int v = 3; v < partsOfProjectPath.length; v++) {
                    displayableProjectPath += File.separator + finalPartsOfProjectPath[v];
                }
            }
            Text projectPath = new Text(displayableProjectPath);
            Tooltip tooltip = new Tooltip(displayableProjectPath);
            Tooltip.install(projectPath, tooltip);

            recentlyUsedProjectsBtn.getChildren().add(projectPath);

            /* third part of the Button = date the project was last used */
            HBox lastUsed = new HBox();
            String lastOpened = recentlyUsedProjects.get(i).getEdited().toString();
            String partsOfDate[] = lastOpened.split(" ");
            String dateString = partsOfDate[0] + " " + partsOfDate[1] + " " + partsOfDate[2] + "\t" + partsOfDate[3];
            Text date = new Text(dateString);
            Text textForLastUsed = new Text("Zuletzt geöffnet am:\t");
            lastUsed.getChildren().addAll(textForLastUsed, date);
            recentlyUsedProjectsBtn.getChildren().add(lastUsed);

            /* Styling */
            recentlyUsedProjectsBtn.setPrefSize(300, 70);
            recentlyUsedProjectsBtn.setStyle("-fx-background-color: deepskyblue; -fx-text-fill: #585858; " +
                    "-fx-border-width: 0 0 1 0; -fx-border-color: deepskyblue deepskyblue #585858 deepskyblue; " +
                    "-fx-alignment: baseline-left; -fx-font-size: 13;");
            recentlyUsedProjectsBtn.setPadding(new Insets(0, 0, 0, 20));
            projectname.setFill(Color.web("#585858"));
            projectname.setStyle("-fx-font-weight: bold;");
            projectnamebox.setPadding(new Insets(0, 0, 0, -10));
            projectPath.setFill(Color.web("#585858"));
            lastUsed.setPadding(new Insets(10, 0, 0, 0));
            textForLastUsed.setFill(Color.web("#585858"));
            date.setFill(Color.web("#585858"));

            /* Event handler for click on the VBox recentlyUsedProjectsBtn */
            recentlyUsedProjectsBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    BackendAPI.project().openProject(fullProjectPath);
                    event.consume();

                    // Show Main Window
                    App.openMain();
                }
            });


            /*
            final VBox finalBtn = recentlyUsedProjectsBtn;

            finalBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    finalBtn.setStyle("-fx-background-color: #585858; -fx-text-fill: white; " +
                            "-fx-border-width: 0 0 1 0; -fx-border-color: #585858 #585858 white #585858; " +
                            "-fx-alignment: baseline-left; -fx-font-size: 13;");

                }
            });
            finalBtn.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    finalBtn.setStyle("\"-fx-background-color: deepskyblue; -fx-text-fill: #585858;"  +
                            "-fx-border-width: 0 0 1 0; -fx-border-color: deepskyblue deepskyblue #585858 deepskyblue;" +
                            "-fx-alignment: baseline-left; -fx-font-size: 13;\"");
                }
            });
            */

            grid.add(recentlyUsedProjectsBtn, 0, i + 1);
        }
    }

}
