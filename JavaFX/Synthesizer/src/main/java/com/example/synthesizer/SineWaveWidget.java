package com.example.synthesizer;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class SineWaveWidget extends AudioComponentWidget {
    private Slider freqSlider;
    private Label freqLabel;

    public SineWaveWidget(AudioComponent sineWave, AnchorPane parent) {
        super(sineWave, parent, "SineWave");

        // Create Slider and Label for SineWaveWidget
        freqSlider = new Slider(1, 1600, 300);
        freqLabel = new Label("SineWave"); // Initial label value
        freqLabel.setStyle("-fx-font-family:'Times New Roman';-fx-font-size:  12; -fx-font-weight: bold");

        // Add Slider and Label to the widget
        leftside_.getChildren().add(freqLabel);
        leftside_.getChildren().add(freqSlider);

        // Set event handler for the Slider
        freqSlider.setOnMouseDragged(this::handleSlider);
        this.setLayoutX(50);
        this.setLayoutY(50);
    }

    private void handleSlider(MouseEvent e) {
        int result = (int) freqSlider.getValue();
        freqLabel.setText("SineWave " + result + "Hz");
        ((SineWave) audComp_).setFrequency(freqSlider.getValue());
    }




}


