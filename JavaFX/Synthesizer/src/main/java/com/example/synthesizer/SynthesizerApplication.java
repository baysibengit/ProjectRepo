package com.example.synthesizer;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
//import java.awt.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;



public class SynthesizerApplication extends Application {

    public static ArrayList<AudioComponentWidget> widgets_ = new ArrayList<>();
    public static ArrayList<AudioComponentWidget> Connected_widgets_ = new ArrayList<>();
    public static Circle speaker;
    public static  AnchorPane mainCenter = new AnchorPane();
    @Override
    public void start(Stage stage) throws IOException {
        //Create a border pane for the main layout
        BorderPane mainLayout = new BorderPane();

        //Right Panel
        VBox rightPanel = new VBox();
        //Add Color
        rightPanel.setStyle("-fx-background-color:#9644e0");
        //Padding
        rightPanel.setPadding(new Insets(4));
        //Sinewave Button******************************************
        Button sinWaveButton = new Button("SineWave");
        //Attach action
        sinWaveButton.setOnAction(e->createSineWaveWidget(e));//Array list of buttons and pull with label, handle button click function and try to pass it into create and try to print out to console what type of button is being clicked
        rightPanel.getChildren().add(sinWaveButton);
        // mainLayout.setRight(rightPanel); todo check
        //VolumeAdjuster Button************************************
        Button volumeAdjButton = new Button("VolumeAdjuster");
        //Attach action
        volumeAdjButton.setOnAction(e->createVolumeAdjusterWidget(e));
        rightPanel.getChildren().add(volumeAdjButton);
        //Mixer button ********************************************
        Button mixerButton = new Button("Mixer");
        //Attach action
        mixerButton.setOnAction(e->createMixerWidget(e));
        rightPanel.getChildren().add(mixerButton);
        //Linear Ramp Button **************************************
        Button linRampButton = new Button("LinearRamp");
        //Attach action
        rightPanel.getChildren().add(linRampButton);
        //Variable Frequency Wave Generator Button*****************
        Button VFWaveButton = new Button("VFWaveGenerator");
        //Attach action
        rightPanel.getChildren().add(VFWaveButton);
        mainLayout.setRight(rightPanel);






        //Center panel
        //Define as member variable if issues are encountered
        mainCenter.setStyle("-fx-background-color: #5858da");
        mainLayout.setCenter(mainCenter);
        //Create speaker button
        speaker = new Circle(400,200,15);
        //Give color
        speaker.setFill(Color.BLACK);
        //Push into anchor pane
        mainCenter.getChildren().add(speaker);

        //Bottom panel
        HBox bottomPanel = new HBox();
        bottomPanel.setStyle("-fx-background-color: rgba(255,255,255,0.29)");
        Button playButton = new Button("Play");
        bottomPanel.getChildren().add(playButton);
        playButton.setOnAction(e -> playAudio(e));
        mainLayout.setBottom(bottomPanel);

        //load main layout and run GUI
        Scene scene = new Scene(mainLayout, 600, 400);
        stage.setTitle("Synthesizer");
        stage.setScene(scene);
        stage.show();
    }


    private void playAudio(ActionEvent e) {
        try {

            AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);
            for (AudioComponentWidget w: Connected_widgets_ ) {

                Clip c = AudioSystem.getClip();
                byte[] data = w.audComp_.getClip().getData();

                c.open(format16, data, 0, data.length);
                c.start();

                AudioListener listener = new AudioListener(c);
                c.addLineListener(listener);

                System.out.println("Clip from Connected_widgets_ was played.");

            }

            System.out.println("SineWave added to Connected_Widgets_.");
            System.out.println("Connected_widgets_ size: " + Connected_widgets_.size() );

        } catch (LineUnavailableException k) {
            System.out.println(k.getMessage());
        }
    }

    private void createSineWaveWidget(ActionEvent e) {
        AudioComponent sinewave = new SineWave(200);
        AudioComponentWidget acw = new SineWaveWidget(sinewave, mainCenter);
        mainCenter.getChildren().add(acw);
//        Connected_widgets_.add(acw);
    }

    private void createVolumeAdjusterWidget(ActionEvent e) {
        AudioComponent volumeAdjuster = new VolumeAdjuster(1);
        AudioComponentWidget acwV = new VolumeAdjusterWidget(volumeAdjuster, mainCenter);
        mainCenter.getChildren().add(acwV);
        widgets_.add(acwV);
       //Connected_widgets_.add(acwV);
    }

    private void createMixerWidget(ActionEvent e) {
        AudioComponent mixer = new Mixer();
        AudioComponentWidget acwM = new MixerWidget(mixer, mainCenter);
        mainCenter.getChildren().add(acwM);
        widgets_.add(acwM);
        // Connected_widgets_.add(acwV);
    }


    public static void main(String[] args) {
        launch();
    }
}