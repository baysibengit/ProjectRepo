package com.example.synthesizer;

import java.util.ArrayList;

public class Mixer implements  AudioComponent{
    private ArrayList<AudioClip> MixerofAudComps;

    //Constructor
    public Mixer(){
        this.MixerofAudComps = new ArrayList<>();
        //change name

    }

    @Override
    public AudioClip getClip() {
        AudioClip resultClip = new AudioClip();
            //edit
        for (AudioClip iteratorComp: MixerofAudComps){
            for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++){
                //set sample for result clip with component samples at index of components within MixerofAudComps
                resultClip.setSample(i,(iteratorComp.getSample(i) + resultClip.getSample(i)));
                
            }
        }

        return resultClip;
    }

    @Override
    public boolean hasInput() {
        if (MixerofAudComps.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void connectInput(AudioComponent input) {

        //Transform to audio clip for speed
        AudioClip result = input.getClip();
        MixerofAudComps.add(result);

    }
}
