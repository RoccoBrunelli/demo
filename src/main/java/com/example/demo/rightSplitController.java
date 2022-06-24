package com.example.demo;

import com.example.demo.unchanged.*;
import javafx.fxml.FXML;

import java.util.Arrays;
import java.util.List;

public class rightSplitController {
    @FXML public otherPlayersController player1Controller;
    @FXML public otherPlayersController player2Controller;
    @FXML public otherPlayersController player3Controller;
    private GUI gui;
    private GUIController guiController;

    @FXML
    public void initialize(){
        //TODO eliminate
        Player p1 = new Player(0);
        Player p2 = new Player(1);
        Player p3 = new Player(3);
        p1.getGameBoard().addStudentsEnter(List.of(Color.GREEN, Color.YELLOW, Color.BLUE));
        p1.getGameBoard().addStudentsEnter(Arrays.stream(Color.values()).toList());
        p1.getHand().setCardPlayed(new Card(2));
        p1.getGameBoard().profControlled.add(new Professor(Color.BLUE, 0));
        p1.getGameBoard().profControlled.add(new Professor(Color.RED, 0));
        p1.getGameBoard().addTower(List.of(new Tower(TColor.WHITE, 0), new Tower(TColor.BLACK, 0), new Tower(TColor.GREY, 0)));
        p2.getGameBoard().addTower(List.of(new Tower(TColor.GREY, 1)));
        p3.getGameBoard().addTower(List.of(new Tower(TColor.BLACK, 2)));
        List<Player> players = List.of(p1,p2,p3);
        update(players);
    }


    public void update(List<Player> players){
        int numPlayer = players.size();
        player1Controller.update(players.get(0));
        if(numPlayer > 1)
            player2Controller.update(players.get(1));
        if(numPlayer > 2)
            player3Controller.update(players.get(2));
    }

    public void setGui(GUI gui){
        this.gui = gui;
    }
    public void setGuiController(GUIController controller) {
        this.guiController = controller;
    }
}
