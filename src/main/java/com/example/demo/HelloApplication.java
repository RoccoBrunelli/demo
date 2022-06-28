package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


public class HelloApplication extends Application {
    private static Stage stage;
    private static Stage popUP;
    private static Scene popUPScene;
    private static URL fxmlpath;

    @Override
    public void start(Stage stage) throws IOException {
        HelloApplication.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlpath);
        AnchorPane scene = fxmlLoader.load();
        Scene Main = new Scene(scene);
        HelloApplication.stage.setScene(Main);
        HelloApplication.stage.show();
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/sources_/fxmls/Personality/Jester/Jester.fxml"));
        popUPScene = new Scene( loader.load());
        popUP = new Stage();
        popUP.setScene(popUPScene);
        popUP.show();
    }

    public static void popUp(String fxmlPath){
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(fxmlPath));
            AnchorPane pane = loader.load();
            popUPScene = new Scene(pane);
            popUP.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setPopUPScene(Scene scene) {
        popUP.setScene(scene);
    }

    public static void closePopUp(){
        popUP.close();
    }

    public static void setSize(double width, double height){
        stage.setWidth(width);
        stage.setHeight(height);
    }

    public static void main(String[] args) {
        fxmlpath = HelloApplication.class.getResource("/sources_/fxmls/MainSplits/main_screen_4players_AdvMode.fxml");
        launch();
    }
}