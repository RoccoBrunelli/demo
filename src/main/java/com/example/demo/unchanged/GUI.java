package com.example.demo.unchanged;

import com.example.demo.GuiResources;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GUI extends Application implements View {
    private static int numPlayers;
    private static boolean isAdvancedMode;
    private String username; //we could move this into Client
    private static Stage stage;
    private Mage mage;
    private TColor towerColor;
    private String currentScenePath; //current scene that is showing
    private static final Map<String, Scene> loadedScenes = new HashMap<>();
    private static String starting_screen = GuiResources.startScreen;
    private static GUIInputHandler controller;
    private static FXMLLoader fxmlLoader;
    private static GUIController mainController;

    //launch calls start (method from Application Class)
    public void initialize() {
        launch();
    }

    /**
     * starts the gui and creates a new GUIController. Then sets the current scene as
     * the start_screen scene.
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception { //this method is called by launch()
        stage.setTitle("Eriantys");
        GUI.stage = stage;
        stage.setResizable(false);
        mainController = new GUIController(this);
        setScene(starting_screen);
    }

    public void resetPosition(){
        GUI.getStage().setX(0);
        GUI.getStage().setY(0);
    }

    /**
     * this method loads a scene from an fxmlPath but doesn't show it to the user.
     * This method is used for preloading scenes
     * @param fxmlPath string which contains the path to the fxmlScene
     */
    public void loadScene(String fxmlPath){
        try {
            fxmlLoader = new FXMLLoader(GUI.class.getResource(fxmlPath));
            loadedScenes.put(fxmlPath, new Scene(fxmlLoader.load()));
            controller = fxmlLoader.getController();
            controller.setGui(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method loads a scene in the scene cache and sets the scene and updates
     * the current stage to see the scene.
     * @param fxmlPath scene to be set
     */
    public void setScene(String fxmlPath){
        System.out.println("fxml: " + fxmlPath);
        //if the scene to show is already the one chosen
        if(fxmlPath.equals(currentScenePath))
            return;

        //if the scene has to be loaded
        if(!loadedScenes.containsKey(fxmlPath)){
            loadScene(fxmlPath);
        }

        //when the scene is already loaded in memory
        currentScenePath = fxmlPath;
        Scene currentScene = loadedScenes.get(fxmlPath);
        stage.setScene(currentScene);
        stage.sizeToScene();
        stage.show();
    }

    /**
     * displays a message to the user
     * @param message to be displayed
     */
    public void displayMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.showAndWait();
    }

    public void displayErrorMessage(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

    public void displayUpdate(updateMessage update) {
        //TODO
    }

    public void clearSceneCache(){
        loadedScenes.clear();
    }

    @Override
    public void stop() throws Exception {

    }

    public String getName() { return this.username; }
    public void setName(String name) { this.username = name;}
    public Mage getMage() { return mage; }
    public void setMage(Mage mage) { this.mage = mage; }
    public static int getNumPlayers() { return numPlayers; }
    public static void setNumPlayers(int numPlayers) { GUI.numPlayers = numPlayers; }
    public static boolean isAdvancedMode() { return isAdvancedMode; }
    public static void setAdvancedMode(boolean advancedMode) { isAdvancedMode = advancedMode; }
    public TColor getTowerColor() { return towerColor; }
    public void setTowerColor(TColor towerColor) { this.towerColor = towerColor; }
    public String getCurrentScenePath() { return currentScenePath; }
    public static GUIInputHandler getController() { return controller; }
    public static GUIController getGuiController() { return mainController; }
    public static Stage getStage() { return stage; }
    public void setStartingScreen(String path) { starting_screen = path; }
}
