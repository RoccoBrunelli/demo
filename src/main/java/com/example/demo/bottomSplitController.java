package com.example.demo;

import com.example.demo.unchanged.Personality;
import com.example.demo.unchanged.PersonalityFactory;
import com.example.demo.unchanged.TColor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

public class bottomSplitController {
    private mainController parent;
    @FXML public Text numTurnText;
    @FXML public ImageView YouTurnImage;
    @FXML public ImageView towerImage;
    @FXML public AnchorPane gameboardAnchor;
    @FXML public AnchorPane advancedModePane;
    @FXML public AnchorPane notAdvancedModePane;
    @FXML public ImageView magicCardPlayed;
    @FXML public AnchorPane availableMagicCards;
    @FXML public Line lineDivider;
    public gameBoardController gameboardController;

    public double getMinHeight(){
        return lineDivider.getLayoutY();
    }
    @FXML
    public void initialize(){
        //TODO eliminate
        setYourTurn(true);
        setTowerColor(TColor.GREY);
        setAdvancedMode(true);
        setNumTurn(3);
        List<Personality> personalities = PersonalityFactory.generate(List.of("botanist", "cantor", "archer"));
        setAvailablePersonalities(personalities);
    }
    public void setAdvancedMode(boolean isAdvancedMode){
        if(isAdvancedMode){
            advancedModePane.setOpacity(1);
            advancedModePane.setDisable(false);
            notAdvancedModePane.setOpacity(0);
            notAdvancedModePane.setDisable(true);
        } else {
            advancedModePane.setOpacity(0);
            advancedModePane.setDisable(true);
            notAdvancedModePane.setOpacity(1);
            notAdvancedModePane.setDisable(false);
        }
    }

    /**
     * sets available personality cards images from personality cards name.
     * it expects a list of 3 cards, if the list is longer the other elements will be ignored,
     * if the list is shorter the missing elements will not be replaced (will stay the same as before)
     * @param cards list of cards to be displayed
     */
    public void setAvailablePersonalities(List<Personality> cards){
        for (int i=0; i<3 && i<cards.size(); i++){
            ImageView img = (ImageView) availableMagicCards.getChildren().get(i);
            String path = GuiResources.PersonalityImage(cards.get(i).getName());
            img.setImage(new Image(getClass().getResource(path).toString()));
        }
    }
    public void setTowerColor(TColor color){
        String imgPath = GuiResources.towerImage(color);
        towerImage.setImage(new Image(getClass().getResource(imgPath).toString()));
    }
    public void setNumTurn(Integer numTurn){
        numTurnText.setText(numTurn.toString());
    }

    public void setYourTurn(boolean isYourTurn){
        String imgPath = isYourTurn ? GuiResources.YourTurnTrueSimbol : GuiResources.YourTurnFalseSimbol;
        Image yourTurnimg = new Image(getClass().getResource(imgPath).toString());
        YouTurnImage.setImage(yourTurnimg);
    }

    @FXML
    public void onAssistanceCardClicked(MouseEvent event){
        //TODO select assistance card
        ImageView card = (ImageView) event.getSource();
        card.setDisable(true);
        card.setEffect(new ColorAdjust(0,-1,-0.2,0));
    }

    @FXML
    public void onMagicCardClicked(MouseEvent event) {
        //TODO buy magic card
        ImageView card = (ImageView) event.getSource();
        String personality = GuiResources.personality(GuiResources.personalityNameFromImage(card.getImage().getUrl()));
        HelloApplication.popUp(personality);
        magicCardPlayed.setImage(card.getImage());
    }

    public void setParentController(mainController controller){
        parent = controller;
    }
    public mainController getParentController(){
        return parent;
    }
}
