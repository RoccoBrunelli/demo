package com.example.demo;

import com.example.demo.unchanged.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class gameBoardController {
    //TODO QUANDO FACCIAMO RESIZE SU GAMEBOARD SI SMINCHIA TUTTO
    @FXML public AnchorPane entrance;
    @FXML public AnchorPane towersPane;
    @FXML public AnchorPane hallPane;
    @FXML public AnchorPane professorsPane;
    @FXML public ImageView yellowProf;
    @FXML public ImageView pinkProf;
    @FXML public ImageView redProf;
    @FXML public ImageView greenProf;
    @FXML public ImageView blueProf;
    private personalityController personalityController;
    private int maxHallSelection;
    private int maxEntranceSelection;
    private List<Color> entranceSelection = new ArrayList<>();
    private List<Color> hallSelection = new ArrayList<>();
    private GUIController guiController;
    private GUI gui;
    private Map<Color, Integer> hallStudents;

    private ImageView img(Image img){ //TODO eliminate
        ImageView view = new ImageView(img);
        view.setFitHeight(29);
        view.setFitWidth(29);
        return view;
    }
    @FXML
    public void initialize(){
        hallStudents = new HashMap<>();
        //TODO eliminate
        GameBoard gb = new GameBoard(1);
        gb.towers.addAll(List.of(new Tower(TColor.GREY, 1), new Tower(TColor.BLACK, 1), new Tower(TColor.WHITE, 1)));
        gb.profControlled.addAll(List.of(new Professor(Color.GREEN, 1), new Professor(Color.BLUE, 1)));
        gb.addStudentHall(Color.BLUE);
        gb.addStudentHall(Color.GREEN);
        gb.addStudentHall(Color.GREEN);
        gb.addStudentHall(Color.PINK);
        gb.addStudentsEnter(List.of(Color.RED, Color.YELLOW, Color.YELLOW, Color.BLUE, Color.GREEN, Color.PINK, Color.BLUE));
        update(gb);
    }

    public void disableDrag(){
        entrance.setDisable(true);
        hallPane.setDisable(true);
    }

    public void update(GameBoard gb){
        if(gb == null)
            return;
        setTowers(gb.getTowers());
        setProfessors(gb.getProf());
        setStudentsHall(gb.hall);
        setStudentsEnter(gb.getStudentsEnter());
    }

    public void setStudentsEnter(List<Color> students){
        for(int i=0; i<students.size() && i<entrance.getChildren().size(); i++){
            ImageView imgview = (ImageView) entrance.getChildren().get(i);
            String studentPath = getClass().getResource(GuiResources.StudentImage(students.get(i))).toString();
            imgview.setImage(new Image(studentPath));
            imgview.setFitWidth(29);
            imgview.setFitHeight(29);
        }
    }

    public void addStudentsHall(List<Color> students){
        for(Color student: students){
            int n = hallStudents.get(student);
            hallStudents.put(student, n+1);
        }
        setStudentsHall(hallStudents);
    }

    private void setStudentsHall(Map<Color, Integer> students){
        hallStudents = students;
        for(Color c : students.keySet()){
            String id = "#"+c.toString().toLowerCase() + "Students";
            AnchorPane studentsPane = (AnchorPane) hallPane.lookup(id);
            for(int i=0; i<studentsPane.getChildren().size(); i++){
                if(i<students.get(c))
                    studentsPane.getChildren().get(i).setOpacity(1);
                if(i>=students.get(c))
                    studentsPane.getChildren().get(i).setOpacity(0);
            }
        }
    }

    public void setProfessors(List<Professor> professors){
        professorsPane.getChildren().forEach(x->x.setOpacity(0));
        for (Professor p: professors){
            switch (p.getColor()){
                case YELLOW -> yellowProf.setOpacity(1);
                case RED -> redProf.setOpacity(1);
                case PINK -> pinkProf.setOpacity(1);
                case GREEN -> greenProf.setOpacity(1);
                case BLUE -> blueProf.setOpacity(1);
            }
        }
    }

    public void setTowers(List<Tower> towers){
        if(towersPane == null)
            return;
        for(int i = 0; i<towers.size(); i++){
            String path = GuiResources.towerImage(towers.get(i).getColor());
            Image towImg = new Image(getClass().getResource(path).toString());
            ImageView tow = (ImageView) towersPane.getChildren().get(i);
            tow.setImage(towImg);
        }
        for(int j = towers.size(); towers.size() <= j && j <towersPane.getChildren().size(); j++){
            ImageView tow = (ImageView) towersPane.getChildren().get(j);
            tow.setImage(null);
        }
    }

    @FXML
    public void onDragFromEntrance(MouseEvent event){
        Dragboard db = entrance.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        ImageView student = (ImageView) event.getSource();
        String stud = student.getId();
        content.putString(stud);
        db.setContent(content);
        db.setDragView(student.getImage(), student.getFitHeight()/2 , student.getFitWidth()/2);
        event.consume();
    }

    @FXML
    public void onDragHallDropped(DragEvent event){
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            AnchorPane enter = (AnchorPane) event.getGestureSource();
            ImageView student = (ImageView) enter.lookup("#" + db.getString());

            //get student color
            Color colorSelected = GuiResources.colorFromImage(student.getImage().getUrl());

            //move student to hall in the view
            addStudentsHall(List.of(colorSelected));
            //TODO move student to hall in the model

            //remove student from enter
            student.setImage(null);
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    public void onDragOver(DragEvent event){ //necessary for drag and drop
        if(event.getDragboard().hasString()){
            event.acceptTransferModes(TransferMode.ANY);
        }
        event.consume();
    }


    @FXML
    public void enterSelectStudent(MouseEvent event){
        ImageView student = (ImageView) event.getSource();
        Color colorSelected = GuiResources.colorFromImage(student.getImage().getUrl());
        if (entranceSelection.contains(colorSelected)){
            student.setEffect(null);
            entranceSelection.remove(colorSelected);
        } else {
            if(entranceSelection.size() >= maxEntranceSelection)
                return;
            student.setEffect(new DropShadow());
            entranceSelection.add(colorSelected);
        }
    }

    @FXML
    public void hallSelectStudent(MouseEvent event){
        ImageView student = (ImageView) event.getSource();
        Color colorSelected = GuiResources.colorFromImage(student.getImage().getUrl());
        if (hallSelection.contains(colorSelected)){
            student.setEffect(null);
            hallSelection.remove(colorSelected);
        } else {
            if(hallSelection.size() >= maxHallSelection)
                return;
            student.setEffect(new DropShadow());
            hallSelection.add(colorSelected);
        }
    }

    public void setGui(GUI gui){
        this.gui = gui;
    }
    public void setGuiController(GUIController controller) {
        this.guiController = controller;
    }
    public void setPersonalityController(personalityController personalityController) {
        this.personalityController = personalityController;
    }

}
