package com.example.demo;

import com.example.demo.unchanged.Color;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class personalityController {
    private int islandChosen = 0;
    private List<ImageView> jesterSelection = new ArrayList<>();
    private Color gluttonSelection;
    private ImageView courtesanSelection;
    private ImageView botanistSelection;
    @FXML public AnchorPane isolette;
    @FXML public AnchorPane plancia;
    @FXML public Text Cost;
    @FXML public AnchorPane gluttonPanel;
    @FXML public AnchorPane courtesanPanel;
    @FXML public topSplitController islansController;
    @FXML public AnchorPane secondscreen;
    @FXML public gameBoardController gameBoardController;

    @FXML
    public void initialize(){
        System.out.println("personality: " + islansController);
        if(islansController != null)
            islansController.setPersonalityController(this);
        if(gameBoardController != null)
            gameBoardController.setPersonalityController(this);
    }

    @FXML
    public void jesterSelectStudent(MouseEvent event){
        ImageView student = (ImageView) event.getSource();
        if(jesterSelection.contains(student)){
            student.setEffect(null);
            jesterSelection.remove(student);
        } else {
            if(jesterSelection.size()>=3)
                return; //maybe add a text that says "max 3 students"
            student.setEffect(new DropShadow());
            jesterSelection.add(student);
        }
        event.consume();
    }

    @FXML
    public void gluttonSelect(MouseEvent event){
        Node colorNode = (Node) event.getSource();
        Node prevSelection = gluttonPanel.lookup("#"+String.valueOf(gluttonSelection).toLowerCase());
        Color selection = Color.valueOf(colorNode.getId().toUpperCase());
        if(gluttonSelection!= null && prevSelection!= null)
            prevSelection.setEffect(null);
        colorNode.setEffect(new DropShadow());
        gluttonSelection = selection;
    }
    @FXML
    public void gluttonConfirmSelection(){
        HelloApplication.closePopUp();
        //TODO
    }

    @FXML
    public void courtesanSelect(MouseEvent event){
        ImageView student = (ImageView) event.getSource();
        if(courtesanSelection != null)
            courtesanSelection.setEffect(null);
        courtesanSelection = student;
        courtesanSelection.setEffect(new DropShadow());
    }
    @FXML
    public void courtesanConfirmSelection(){
        Color colorSelected = GuiResources.colorFromImage(courtesanSelection.getImage().getUrl());
        HelloApplication.closePopUp();
        //TODO
    }

    @FXML
    public void botanistSelect(MouseEvent event){
        ImageView tessera = (ImageView) event.getSource();
        if(botanistSelection != null)
            botanistSelection.setEffect(null);
        botanistSelection = tessera;
        botanistSelection.setEffect(new DropShadow());
    }

    @FXML
    public void nextScreen(){
        secondscreen.setOpacity(1);
        secondscreen.setMouseTransparent(false);
        secondscreen.setDisable(false);
        secondscreen.setVisible(true);

        //HelloApplication.popUp("sources_/fxmls/Personality/Jester/Jester_screen2.fxml");
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
    public void setCost(int n) { Cost.setText(String.valueOf(n)); }
}
