package com.example.demo;

import com.example.demo.unchanged.Color;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class personalityController {
    private int Cost;
    private int islandChosen = 0;
    private List<Color> jesterSelection = new ArrayList<>();
    @FXML public topSplitController islandsController; //this shouldn't be topSplitController
    @FXML public AnchorPane secondscreen;
    @FXML public gameBoardController gameBoardController;

    @FXML
    public void initialize(){
        if(islandsController != null)
            islandsController.setPersonalityController(this);
        if(gameBoardController != null)
            gameBoardController.setPersonalityController(this);
    }

    @FXML
    public void jesterSelectStudent(MouseEvent event){
        ImageView student = (ImageView) event.getSource();
        System.out.println("colorFromImage");
        Color colorSelected = GuiResources.colorFromImage(student.getImage().getUrl());
        System.out.println("student_effect");
        if(jesterSelection.contains(colorSelected)){
            student.setEffect(null);
            jesterSelection.remove(colorSelected);
        } else {
            if(jesterSelection.size()>=3)
                return; //maybe add a text that says "max 3 students"
            student.setEffect(new DropShadow());
            jesterSelection.add(colorSelected);
        }
    }

    @FXML
    public void nextScreen(){
        secondscreen.setOpacity(1);
        secondscreen.setMouseTransparent(false);
        secondscreen.setDisable(false);
    }

    @FXML
    public void confirmGameBoardSelection(ActionEvent event){
        HelloApplication.closePopUp();
        //TODO
    }

    @FXML
    public void confirmIslandSelection(ActionEvent event){
        HelloApplication.closePopUp();
        //TODO
    }

    public void islandChoice(int n) { islandChosen = n; }
    public void setCost(int n) { Cost = n; }
}
