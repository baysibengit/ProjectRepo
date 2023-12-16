import com.example.synthesizer.AudioClip;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AudioClipTest {

    @Test
    void getSample() {
      //Test regular value
      AudioClip audioClip = new AudioClip();
      audioClip.setSample(1,200);
      //Test Max
      audioClip.setSample(2,32767);
      //Test Min
      audioClip.setSample(3,-32768);

      for (int i = Short.MIN_VALUE; i < Short.MAX_VALUE; i++ ){
          audioClip.setSample(10, i);
          Assertions.assertEquals(i, audioClip.getSample(10));
      }


        Assertions.assertEquals(200,audioClip.getSample(1));
        Assertions.assertEquals(32767, audioClip.getSample(2));
        Assertions.assertEquals(-32768, audioClip.getSample(3));
    }
    @Test
    void setSample() {
        AudioClip audioClip = new AudioClip();
        audioClip.setSample(1,100);

        Assertions.assertEquals(100, audioClip.getSample(1));
    }
}