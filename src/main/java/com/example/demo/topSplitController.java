package com.example.demo;

import com.example.demo.unchanged.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class topSplitController {
    private personalityController personallyController;
    private int selectedIsland = 0;
    @FXML public AnchorPane islandsPane;
    @FXML public AnchorPane cloudsPane;

    @FXML
    public void initialize(){
        //TODO eliminate
        Game game = new Game(1,4);
        //islands
        game.islands.get(0).listTowers.add(new Tower(TColor.BLACK, 0));
        game.islands.get(8).listTowers.add(new Tower(TColor.BLACK, 0));
        game.islands.get(1).addStudents(List.of(Color.BLUE, Color.GREEN, Color.PINK));
        game.islands.get(11).addStudents(List.of(Color.YELLOW, Color.RED));
        game.islands.get(2).addStudents(List.of(Color.RED, Color.RED));
        game.islands.get(3).addStudents(List.of(Color.YELLOW, Color.BLUE, Color.GREEN));

        //clouds
        updateMessage gamemsg = new updateMessage(game);
        gamemsg.clouds = new HashMap<>();
        gamemsg.clouds.put(0, List.of(Color.BLUE, Color.YELLOW, Color.RED));
        gamemsg.clouds.put(1, List.of(Color.BLUE, Color.GREEN, Color.BLUE));
        updateClouds(gamemsg.clouds);
        updateIslands(gamemsg.islands);
        updateMother(11);
        System.out.println(gamemsg.mother);
    }

    public void updateMother(int motherPos){
        for(Node island: islandsPane.getChildren()){
            if(!island.getId().equals("island"+motherPos)){
                island.lookup("#mother").setOpacity(0);
                island.lookup("#mother").setDisable(true);
            }
            else{
                island.lookup("#mother").setOpacity(1);
                island.lookup("#mother").setDisable(false);
            }
        }
    }


    public void updateIslands(List<Island> islands){
        islandsPane.getChildren().forEach(x->x.setOpacity(0));
        for(Island island: islands){
            Node islandNode = islandsPane.lookup("#island"+island.getID());
            islandNode.setOpacity(1);
            setIslandTower(islandNode, island.getTowers());
            setIslandStudents(islandNode, island.getStudents());
        }
    }
    private void setIslandTower(Node islandNode, List<Tower> towers){
        ImageView towerImg = (ImageView) islandNode.lookup("#tower");
        Text towerText = (Text) islandNode.lookup("#towerNum");
        if(towers == null || towers.size()== 0){
            towerImg.setOpacity(0);
            towerText.setOpacity(0);
        } else if(towers.size() == 1){
            towerImg.setOpacity(1);
            towerText.setOpacity(0);
        } else {
            towerImg.setOpacity(1);
            towerText.setOpacity(1);
            towerText.setText(String.valueOf(towers.size()));
        }
    }
    private void setIslandStudents(Node islandNode, List<Color> students){
        for(Color color : Color.values()){
            int count = (int) students.stream().filter(x->x.equals(color)).count();
            Text number = (Text) islandNode.lookup("#"+color.toString().toLowerCase()+"StudentsNum");
            number.setText(String.valueOf(count));
            Node student = islandNode.lookup("#"+color.name().toLowerCase()+"Student");
            if(count == 0){
                student.setOpacity(0);
                number.setOpacity(0);
            } else if(count == 1) {
                student.setOpacity(1);
                number.setOpacity(0);
            } else{
                student.setOpacity(1);
                number.setOpacity(1);
            }
        }
    }
    private void addStudentToIsland(AnchorPane island, Color student){
        Text number = (Text) island.lookup("#"+student.toString().toLowerCase()+"StudentsNum");
        int count = Integer.parseInt(number.getText()) + 1;
        island.lookup("#"+student.name().toLowerCase()+"Student").setOpacity(1);
        number.setText(String.valueOf(count));
        System.out.println("count:" + count);
        if(count == 1) {
            number.setOpacity(0);
        } else{
            number.setOpacity(1);
        }
    }
    public void updateClouds(Map<Integer,List<Color>> clouds){
        if(cloudsPane == null)
            return;
        for (int i=0; i<cloudsPane.getChildren().size(); i++){
            AnchorPane cloud = (AnchorPane) cloudsPane.lookup("#cloud"+i);
            List<Node> slots = cloud.getChildren().filtered(x->!"cloudimg".equals(x.getId()));
            if(clouds.get(i) == null){
                cloud.setDisable(true);
            } else {
                cloud.setDisable(false);
            }
            for(int j=0; j<slots.size(); j++){
                ImageView slot = (ImageView) slots.get(j);
                if(clouds.get(i) == null || j >= clouds.get(i).size()){
                    slot.setImage(null);
                } else {
                    String studPath = GuiResources.StudentImage(clouds.get(i).get(j));
                    Image student = new Image(getClass().getResource(studPath).toString());
                    slot.setImage(student);
                }
            }
        }
    }

    @FXML
    public void onDragOver(DragEvent event){ //necessary for drag and drop
        String dbString = event.getDragboard().getString();
        if (dbString == null) {
            event.consume();
            return;
        }
        if(dbString.contains("Student")){
            event.acceptTransferModes(TransferMode.ANY);
            Node islandOver = (Node) event.getSource();
            islandOver.setEffect(new Glow(0.5));
        }
        if(dbString.contains("mother")) {
            System.out.println("mother enter");
            int thisID = Integer.parseInt(((Node)event.getSource()).getId().split("island")[1]);
            int motherID = Integer.parseInt(dbString.split("mother")[1].split("island")[1]);
            int maxMoves = 3;
            int minID = thisID - maxMoves;
            if((minID > 0 && minID <= motherID && motherID <= thisID) ||
                minID <= 0 && (12 + minID <= motherID || motherID <= thisID)){
                event.acceptTransferModes(TransferMode.ANY);
                Node islandOver = (Node) event.getSource();
                islandOver.setEffect(new Glow(0.5));
            }
            System.out.println("mother exit");
        }
        event.consume();
    }

    @FXML
    public void onDragExit(DragEvent event){
        Node islandOver = (Node) event.getSource();
        islandOver.setEffect(null);
        event.consume();
    }

    @FXML
    public void onDragDropped(DragEvent event){
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString() && db.getString().contains("mother")){
            String islandID = "#" + db.getString().split("mother")[1];
            AnchorPane islandFrom = (AnchorPane) islandsPane.lookup(islandID);
            AnchorPane islandTo = (AnchorPane) event.getSource();
            islandFrom.lookup("#mother").setOpacity(0);
            islandTo.lookup("#mother").setOpacity(1);
            islandFrom.lookup("#mother").setDisable(true);
            islandTo.lookup("#mother").setDisable(false);
        }
        if (db.hasString() && db.getString().contains("Student")) {
            AnchorPane enter = (AnchorPane) event.getGestureSource();
            ImageView student = (ImageView) enter.lookup("#" + db.getString());

            //move student to island clientSide
            Color colorSelected = GuiResources.colorFromImage(student.getImage().getUrl());
            AnchorPane island = (AnchorPane) event.getSource();
            addStudentToIsland(island, colorSelected);

            //TODO move student to island in the model

            //remove student from enter
            student.setImage(null);
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    public void setPersonalityController(personalityController controller){
        this.personallyController = controller;
    }

    @FXML
    public void selectIsland(MouseEvent event){
        AnchorPane island = (AnchorPane) event.getSource();
        AnchorPane islandPrec = (AnchorPane) islandsPane.lookup("#island"+selectedIsland);
        island.setEffect(new DropShadow());
        if(islandPrec != null && !islandPrec.equals(island))
            islandPrec.setEffect(null);

        int islandId = Integer.parseInt(island.getId().split("island")[1]);
        System.out.println("islandId" + islandId);
        selectedIsland = islandId;
        System.out.println("topSplit: " + this);
        //this.personallyController.islandChoice(islandId);
    }

    @FXML
    public void selectCloud(MouseEvent event){
        AnchorPane anchor = (AnchorPane) event.getSource();
        int cloudID = Integer.parseInt(anchor.getId().split("cloud")[1]);
        anchor.setEffect(new DropShadow());
    }
}
