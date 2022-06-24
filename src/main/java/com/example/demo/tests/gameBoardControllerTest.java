package com.example.demo.tests;

import com.example.demo.HelloApplication;
import com.example.demo.unchanged.*;

import java.util.List;

class gameBoardControllerTest {
    public static void test1(){
        GameBoard gb = new GameBoard(1);
        gb.towers.addAll(List.of(new Tower(TColor.GREY, 1), new Tower(TColor.BLACK, 1), new Tower(TColor.WHITE, 1)));
        gb.profControlled.addAll(List.of(new Professor(Color.GREEN, 1), new Professor(Color.BLUE, 1)));
        gb.addStudentHall(Color.BLUE);
        gb.addStudentHall(Color.GREEN);
        gb.addStudentHall(Color.GREEN);
        gb.addStudentHall(Color.PINK);
        gb.addStudentsEnter(List.of(Color.RED, Color.YELLOW, Color.YELLOW, Color.BLUE, Color.GREEN, Color.PINK, Color.BLUE));
        HelloApplication.main(new String[]{"/sources_/fxmls/right_split_4players_AdvMode.fxml"});
    }
}