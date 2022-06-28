package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;

public class islandController {

    @FXML
    public void onDragMother(MouseEvent event){
        ImageView mother = (ImageView) event.getSource();
        Dragboard db = mother.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        content.putString("mother"+mother.getParent().getParent().getId());
        db.setContent(content);
        db.setDragView(mother.getImage(), mother.getFitHeight()/2 , mother.getFitWidth()/2);
        event.consume();
        System.out.println("onDragMother exit");
    }
}
