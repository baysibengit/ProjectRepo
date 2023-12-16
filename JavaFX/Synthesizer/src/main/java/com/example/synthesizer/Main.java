package com.example.synthesizer;
import javax.sound.sampled.*;

public class Main {
    public static void main(String[] args) throws LineUnavailableException {


        // Get properties from the system about samples rates, etc.
// AudioSystem is a class from the Java standard library.
        Clip c = AudioSystem.getClip(); // Note, this is different from our AudioClip class.

        // This is the format that we're following, 44.1 KHz mono audio, 16 bits per sample.
        AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);
        //Creating clips of SineWave
        AudioComponent genSin = new SineWave(440); // Your code
        AudioClip clip = genSin.getClip();         // Your code
        AudioComponent genSin2 = new SineWave(100);
        AudioClip clip1 = genSin2.getClip();
        //Creat mixer component
        AudioComponent mixer = new Mixer();
        //Connect inputs from different components
        mixer.connectInput(genSin);
        mixer.connectInput(genSin2);
        //Creating VolumeAdjuster component
        AudioComponent volAdjuster = new VolumeAdjuster(2f);
        //Connecting mixer to volume adjuster
        volAdjuster.connectInput(mixer);
        //Creating audio clip with mixed input and set volume
        AudioClip clip2 = volAdjuster.getClip();
        //Create linear ramp audio component
        AudioComponent linramp = new LinearRamp(50,2000);
        //Create Variable frequency wave generator audio component
        AudioComponent freqWaveGen = new VariableFrequencyWaveGenerator();
        //Connect linear ramp to freqwave generator
        freqWaveGen.connectInput(linramp);
        //Make clip for freq
        AudioClip clip3 = freqWaveGen.getClip();





        c.open(format16, clip2.getData(), 0, clip2.getData().length); // Reads data from our byte array to play it.

        System.out.println("About to play...");
        c.start(); // Plays it.
        c.loop(0); // Plays it 2 more times if desired, so 6 seconds total

// Makes sure the program doesn't quit before the sound plays.
        while (c.getFramePosition() < AudioClip.TOTAL_SAMPLES || c.isActive() || c.isRunning()) {
            // Do nothing while we wait for the note to play.
        }

        System.out.println("Done.");
        c.close();

    }

}
