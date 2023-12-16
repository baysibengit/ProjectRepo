package com.example.synthesizer;

public class VolumeAdjuster implements AudioComponent {
    public  float scale;
    private AudioComponent inputAC;


    //Volume adjuster constructor
    public VolumeAdjuster(float scale) {
        this.scale = scale; //adjusts volume
    }

    @Override
    public AudioClip getClip() {
        AudioClip inputClip = inputAC.getClip();
        //result clip to store volume component clip
        AudioClip result = inputAC.getClip();

        for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
            //integer variable to store updated value
            int updatedValue = (int) (scale * inputClip.getSample(i));
            //Clamping
            if (updatedValue > Short.MAX_VALUE) {
                updatedValue = Short.MAX_VALUE;
            } else if (updatedValue < Short.MIN_VALUE) {
                updatedValue = Short.MIN_VALUE;
            }
            //updating result variables sample
            result.setSample(i, updatedValue);
        }
        return result;
    }

        @Override
        public boolean hasInput () {
            return true;

        }

        @Override
        public void connectInput (AudioComponent input){
                inputAC = input;
        }

    public void setScale(float value) {
        this.scale = value;
    }
    }

