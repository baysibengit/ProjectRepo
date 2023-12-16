package com.example.synthesizer;

import java.util.Arrays;

public class AudioClip {

    //Duration constant variable 2 seconds
    private static final int duration = 2;
    //SampleRate constant variable 44100 samples per two seconds
    private static final int sampleRate = 44100;
    //Total sample amount variable
    public static final int TOTAL_SAMPLES = duration * sampleRate;
    //byte array member variable
    private byte[] audioArray = new byte[duration * sampleRate * 2];
    //Method to retrieve get sample index
    public int getSample(int index){
        int least = audioArray[2 * index] & 0XFF;
        int most = audioArray[2 * index + 1] & 0XFF;
        most = most << 8;
        int sample = most | least;
        return (short)sample;
    }
    //Method that return/set the sample passed as an int
    public void setSample(int index, int value){
        byte least = (byte) (value & 0xFF);
        byte most = (byte) (value >> 8);
        audioArray[2 * index] = least;
        audioArray[2 * index + 1] = most;
    }
    //
    public byte[] getData(){return Arrays.copyOf(audioArray, audioArray.length);}

}
