package com.example.synthesizer;

import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

import java.util.ArrayList;


public class AudioComponentWidget extends Pane {


    AudioComponent audComp_;
    AnchorPane parent_;
    Line line_;
    public String label_;
    Circle output_;
    Slider slider_;
    public VBox leftside_;
    public HBox baseLayout_;
    public VBox inputBox_;
    public Circle inputCircle_;
    public Bounds inputBounds_;




    double mouseXPos, mouseYPos, widgetXPos, widgetYPos;
    //constructor
    public AudioComponentWidget(AudioComponent audComp, AnchorPane parent, String label){
        audComp_ = audComp;
        parent_ = parent;

        baseLayout_ = new HBox();
        baseLayout_.setStyle("-fx-background-color: rgba(255,255,255,0.99)");

        VBox rightSide = new VBox();
        //Output button and exit button
        Button closeButton = new Button("x");
        closeButton.setOnAction(e ->closeWidget(e));
        output_ = new Circle(10);
        output_.setFill(Color.AQUA);


        rightSide.getChildren().add(closeButton);
        rightSide.getChildren().add(output_);


        rightSide.setAlignment(Pos.CENTER);
        rightSide.setPadding(new Insets(5));
        rightSide.setSpacing(5);

        //Make input box
        inputBox_ = new VBox();


        //handle drawing the line - handle 3 events, output is speaker widget
        output_.setOnMousePressed(e->startConnection(e, output_));
        output_.setOnMouseDragged(e->moveConnection(e, output_));
        output_.setOnMouseReleased(this::endConnection);

        //Vbox to store sine-wave widget information
        leftside_ = new VBox();




        //Add functionality to and within widget boxes
        leftside_.setOnMousePressed(e->getPosInfo(e));
        leftside_.setOnMouseDragged(e->moveWidget(e));
        baseLayout_.getChildren().add(inputBox_);
        baseLayout_.getChildren().add(leftside_);
        baseLayout_.getChildren().add(rightSide);
        this.getChildren().add(baseLayout_);
    }


    public boolean lineIntersectsAnyNode() {
        for (Node node : parent_.getChildren()) {
            if (this instanceof SineWaveWidget && node instanceof MixerWidget) {
                MixerWidget shape = (MixerWidget) node;
                Bounds mixerBounds = shape.localToScene(shape.getBoundsInLocal());
                if (mixerBounds.intersects(line_.localToScene(line_.getBoundsInLocal()))) {
                    Mixer result = (Mixer) shape.audComp_;
                    result.connectInput(this.audComp_);
                    return true;
                }
            }
            if (this instanceof SineWaveWidget && node instanceof VolumeAdjusterWidget) {
                VolumeAdjusterWidget shape = (VolumeAdjusterWidget) node;
                Bounds mixerBounds = shape.localToScene(shape.getBoundsInLocal());
                if (mixerBounds.intersects(line_.localToScene(line_.getBoundsInLocal()))) {
                    VolumeAdjuster result = (VolumeAdjuster) shape.audComp_;
                    result.connectInput(this.audComp_);
                    System.out.println(result);
                    return true;
                }

            }

            if (this instanceof MixerWidget && node instanceof VolumeAdjusterWidget) {
                VolumeAdjusterWidget shape = (VolumeAdjusterWidget) node;
                Bounds mixerBounds = shape.localToScene(shape.getBoundsInLocal());
                if (mixerBounds.intersects(line_.localToScene(line_.getBoundsInLocal()))) {
                    VolumeAdjuster result = (VolumeAdjuster) shape.audComp_;
                    result.connectInput(this.audComp_);
//                    System.out.println("Mixer Wave Touching Volume True");
//                    SynthesizeApplication.widgets_.add(this);
                    System.out.println("Size of VOlume Mixers " + SynthesizerApplication.widgets_.size());
                    return true;
                }
            }
        }
        return false;
    }

    private void endConnection(MouseEvent e) {
        Circle speaker = SynthesizerApplication.speaker;
        if (this instanceof MixerWidget || this instanceof SineWaveWidget || this instanceof VolumeAdjusterWidget) {

            if (line_.intersects(speaker.getBoundsInParent())) {
                System.out.println(" Line Touching Node");
                SynthesizerApplication.Connected_widgets_.add(this);
            } else if (!lineIntersectsAnyNode()){
                parent_.getChildren().remove(line_);
                line_ = null;
            }
        }

    }


    private void moveConnection(MouseEvent e, Circle output) {
        Bounds parentBounds = parent_.getBoundsInParent();
        line_.setEndX(e.getSceneX() - parentBounds.getMinX());
        line_.setEndY(e.getSceneY() - parentBounds.getMinY());
    }

    private void startConnection(MouseEvent e, Circle output_) {

        Bounds parentBounds = parent_.getBoundsInParent(); // get Bounds
        Bounds bounds = output_.localToScene(output_.getBoundsInLocal());

        //remove line
        if(line_ != null){
            parent_.getChildren().remove(line_);
        }
        //draw Line
        line_ =  new Line();//Move this line to member variable alongside parent *Line line;
        line_.setStrokeWidth(5);

        line_.setStartX(bounds.getCenterX() - parentBounds.getMinX());
        line_.setStartY(bounds.getCenterY() - parentBounds.getMinY());

        line_.setEndX(e.getSceneX());
        line_.setEndY(e.getSceneY());

        parent_.getChildren().add(line_);
    }


    private void moveWidget(MouseEvent e) {

        Bounds parentBounds = parent_.getBoundsInParent();
        Bounds widgetBounds = this.localToScene(this.getBoundsInLocal() );

        double deltaX = e.getSceneX() - mouseXPos;
        double deltaY = e.getSceneY() - mouseYPos;

        if(line_ != null) {
            Bounds outputBounds = output_.localToScene(output_.getBoundsInLocal() );

            line_.setStartX(outputBounds.getCenterX() - parentBounds.getMinX());
            line_.setStartY(outputBounds.getCenterY() - parentBounds.getMinY());

        }

        if(deltaX <= parentBounds.getMinX() ) {
            deltaX = parentBounds.getMinX();
        }

        if(deltaX >= parentBounds.getMaxX() - widgetBounds.getWidth() ) {
            deltaX = parentBounds.getMaxX() - widgetBounds.getWidth();
        }

        if(deltaY <= parentBounds.getMinY() ) {
            deltaY = parentBounds.getMinY();
        }

        if(deltaY >= parentBounds.getMaxY() - widgetBounds.getHeight() ) {
            deltaY = parentBounds.getMaxY() - widgetBounds.getHeight();
        }

        this.relocate(deltaX + widgetXPos, deltaY + widgetYPos);
    }


    private void getPosInfo(MouseEvent e) {
        mouseXPos = e.getSceneX();
        mouseYPos = e.getSceneY();
        widgetXPos = this.getLayoutX();
        widgetYPos = this.getLayoutY();
    }

    private void closeWidget(ActionEvent e){
        parent_.getChildren().remove(this);


        //Remove line upon delete
        SynthesizerApplication.widgets_.remove(this);
        SynthesizerApplication.Connected_widgets_.remove(this);
        parent_.getChildren().remove(line_);
    }

}