package com.example.synthesizer;

public class VariableFrequencyWaveGenerator implements AudioComponent {
   private AudioClip inputRamp;

    @Override
    public AudioClip getClip() {
        AudioClip output = new AudioClip();
        double phase = 0;
        for(int i = 0; i < AudioClip.TOTAL_SAMPLES; i++){
            phase += 2 * Math.PI * inputRamp.getSample(i) / 44100;
            double result = Short.MAX_VALUE * Math.sin(phase);
            output.setSample(i, (int)result);
        }

        return output;
    }

    @Override
    public boolean hasInput() {
        return false;
    }

    @Override
    public void connectInput(AudioComponent input) {
        inputRamp = input.getClip();
    }
}
