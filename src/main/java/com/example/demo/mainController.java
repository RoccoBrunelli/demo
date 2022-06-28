package com.example.demo;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.util.Duration;


import java.io.IOException;

import static com.example.demo.GuiResources.*;

public class mainController {
    @FXML public AnchorPane bottomAnchor;
    @FXML public AnchorPane rightAnchor;
    @FXML public AnchorPane topAnchor;
    @FXML public bottomSplitController bottomAnchorController;
    @FXML public rightSplitController rightAnchorController;
    @FXML public SplitPane rightSplit;
    @FXML public SplitPane bottomSplit;

    @FXML
    public void initialize(){
        HelloApplication.setSize(GuiResources.displayWidth, GuiResources.displayHeight);
        bottomAnchor.setMinHeight(0);
        bottomAnchor.getTransforms().add(new Scale(scalefactor, scalefactor, 0,0));
        rightAnchor.getTransforms().add(new Scale(scalefactor, scalefactor, 0,0));
        rightAnchor.getChildren().add(new AnchorPane(topAnchor));
        onMagicCard();
        //bottomAnchor.getChildren().forEach(this::scale);
        //rightAnchor.getChildren().forEach(this::scale);
        //rightAnchor.setMinWidth(rightAnchor.getMinWidth() + 100);
        //rightAnchor.setMaxWidth(rightAnchor.getMaxWidth() + 100);
        //bottomAnchor.setLayoutX(bottomAnchor.getLayoutX());
        //bottomAnchorController.setParentController(this);
        //rightAnchorController.setParentController(this);
    }

    private void onMagicCard() {
      //HelloApplication.popUp("/sources_/fxmls/Personality/Jester/Jester.fxml");
    }


    @FXML
    public void onDragDropped(DragEvent event){
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasImage()) {
            ImageView img = new ImageView(db.getImage());
            topAnchor.getChildren().add(img);
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    public void onDragOver(DragEvent event){ //necessary for drag and drop
        if(event.getDragboard().hasImage()){
            event.acceptTransferModes(TransferMode.ANY);
        }
        event.consume();
    }

    /**
     * this method returns a timeline that animates the splits to the positions required.
     * If the position entered is negative it ignores that split.
     * @param rightSplitEndPos end position of rightSplit
     * @param bottomSplitEndPos end position of bottomSplit
     * @return Timeline constructed to animate the splits
     */
    private Timeline splitsAnimation(double rightSplitEndPos, double bottomSplitEndPos){
        Timeline timeline = new Timeline(90);
        KeyValue kv1 = new KeyValue(rightSplit.getDividers().get(0).positionProperty(), rightSplitEndPos, Interpolator.EASE_IN);
        KeyValue kv2 = new KeyValue(bottomSplit.getDividers().get(0).positionProperty(), bottomSplitEndPos, Interpolator.EASE_IN);
        KeyFrame kf1 = new KeyFrame(Duration.millis(150), kv1);
        KeyFrame kf2 = new KeyFrame(Duration.millis(150), kv2);
        if(rightSplitEndPos > 0)
            timeline.getKeyFrames().add(kf1);
        if(bottomSplitEndPos > 0)
            timeline.getKeyFrames().add(kf2);
        return timeline;
    }

    //works even if intellij doesn't recognize it
    @FXML
    public void onBottomClick(){
        splitsAnimation(rightSplitClosedPosition,bottomSplitOpenPosition).play();
    }

    @FXML
    public void onRightClick(){
        splitsAnimation(rightSplitOpenPosition,bottomSplitClosedPosition).play();
    }

    @FXML
    public void onTopClick(){
        splitsAnimation(rightSplitClosedPosition,bottomSplitClosedPosition).play();
    }
}