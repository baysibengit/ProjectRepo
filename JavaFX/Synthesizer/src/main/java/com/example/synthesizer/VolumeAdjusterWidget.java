package com.example.synthesizer;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class VolumeAdjusterWidget extends AudioComponentWidget {
    private Slider adjSlider;
    private Label adjLabel;
//    public Circle inputCircle_;
    public Node node;

    public VolumeAdjusterWidget(AudioComponent volumeAdjust, AnchorPane parent) {
        super(volumeAdjust, parent, "VolumeAdjuster");

        //Input button
        inputCircle_ = new Circle(10);
        inputCircle_.setFill(Color.AQUA);
        inputBox_.getChildren().add(inputCircle_);

        //Add functionality to input circle
        //handle drawing the line - handle 3 events, output is speaker widget

       // inputCircle_.setOnMouseReleased(this::endConnection);
       // inputCircle_.setOnMousePressed(this::startConnection);

        //Create Slider and Label for VolumeAdjusterWidget
        adjSlider = new Slider(0, 10, 1);
        adjLabel = new Label("VolumeAdjuster");
        adjLabel.setStyle("-fx-font-family:'Times New Roman';-fx-font-size:  12; -fx-font-weight: bold");
        adjSlider.setOnMouseDragged(this::volumeControl);

        //Add slider and label to vbox
        leftside_.getChildren().add(adjLabel);
        leftside_.getChildren().add(adjSlider);

        //Set event handler for slider

        this.setLayoutX(50);
        this.setLayoutY(50);
    }

    private void volumeControl(MouseEvent e) {
        int result = (int) adjSlider.getValue();
        adjLabel.setText("VolumeAdjuster " + result + "Scale");
        ((VolumeAdjuster) audComp_).setScale((float) adjSlider.getValue());
    }

}










