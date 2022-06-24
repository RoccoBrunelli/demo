package com.example.demo;
import com.example.demo.unchanged.*;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.util.List;

public class otherPlayersController {
    @FXML public Text userName;
    @FXML public Text numIslands;
    @FXML public Text magicCard;
    @FXML public AnchorPane professors;
    @FXML public ImageView towerColor;
    @FXML public ImageView assistantCard;
    @FXML public gameBoardController gameboardController;

    @FXML
    public void initialize(){
    }

    public void setUserName(String userName){
        this.userName.setText(userName);
    }
    public void setMagicCard(Personality card){
        magicCard.setText(card.getName());
    }
    public void setNumIslands(int n){
        numIslands.setText(String.valueOf(n));
    }

    public void update(Player player){
        //update the rest
        setProfessors(player.getGameBoard().getProf());
        //FIXME towerColor setImage relies on the fact that the first slot of a player gameboard is not null this could be a problem
        setTowerColor(player.getGameBoard().getTowers().get(0).getColor());
        setAssistant(player.getHand().getCardPlayed());
        gameboardController.update(player.getGameBoard());
    }

    private void setProfessors(List<Professor> listProf){
        professors.getChildren().forEach(x->x.setOpacity(0));
        for(Professor p: listProf){
            professors.lookup("#"+p.getColor().toString().toLowerCase()+"Prof").setOpacity(1);
        }
    }

    private void setTowerColor(TColor color){
        String url = String.valueOf(getClass().getResource(GuiResources.towerImage(color)));
        if(url.equals("null")){
            towerColor.setImage(new Image(getClass().getResource(GuiResources.YourTurnFalseSimbol).toString()));
            return;
        }
        towerColor.setImage(new Image(url));
    }

    private void setAssistant(Card cardPlayed){
        if(cardPlayed == null){
            assistantCard.setImage(null);
            return;
        }
        String url = getClass().getResource(GuiResources.assistantCardImage(cardPlayed)).toString();
        assistantCard.setImage(new Image(url));
    }
}
