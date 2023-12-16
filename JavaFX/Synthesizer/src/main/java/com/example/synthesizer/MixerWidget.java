package com.example.synthesizer;

import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MixerWidget extends AudioComponentWidget {
    public Label mixerLabel;
    public Circle inputCircle_;
    public MixerWidget(AudioComponent audComp, AnchorPane parent) {
        super(audComp, parent, "Mixer");

        //Create label for MixerWidget
        mixerLabel = new Label("Mixer");
        mixerLabel.setStyle("-fx-font-family:'Times New Roman';-fx-font-size:  12; -fx-font-weight: bold");

        //Add label to the widget
        leftside_.getChildren().add(mixerLabel);



        //Input button
        inputCircle_ = new Circle(10);
        inputCircle_.setFill(Color.AQUA);
        //inputCircle_.setOnMouseReleased(this::endConnection);
        inputBox_.getChildren().add(inputCircle_);

        //set position
        this.setLayoutX(50);
        this.setLayoutX(50);

    }

}


