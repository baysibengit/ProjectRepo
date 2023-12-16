package com.example.synthesizer;

public class LinearRamp implements AudioComponent{
    //Member variables
    private int start;
    private int stop;

    //Constructor
    LinearRamp(int start, int stop){
        this.start = start;
        this.stop = stop;
    }

    @Override
    public AudioClip getClip() {
        //Initialize new audio clip
        AudioClip clip = new AudioClip();
        //Initialize variable to store total samples
        double numSamples = AudioClip.TOTAL_SAMPLES;
        //Iterate through total samples
        for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++){
            //set sample growth scale for you sinewave
            double resultSamp = ( start * ( numSamples - i ) + stop * i ) / numSamples;
            //Call set sample from audio clip class and cast result to int
            clip.setSample(i, (int)resultSamp);
        }
        //Return clip
        return clip;
    }

    @Override
    public boolean hasInput() {
        return false;
    }

    @Override
    public void connectInput(AudioComponent input) {

    }
}
