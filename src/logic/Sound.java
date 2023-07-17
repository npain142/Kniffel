package logic;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

/**
 * Die Sound-Klasse besteht nur aus statischen Methoden.
 * Über jede Methode koennen Sound-Elemente im Programm
 * beeinflusst werden.
 */

public class Sound {

    private static float volume = 0;
    private static FloatControl backgroundControl;

    /**
     * Die Hintergrundmusik hat eine eigene konkrete Methode bekommen,
     * da sie sich von den anderen Soundeffekten in zwei wichtigen aspekten abhebt.
     * ZUm einen laeuft sie im Infinity-Loop und zum anderen sollte es nur einen
     * FloatControl geben, um die Lautstaerke zu veraenderen.
     */
    public static void playBack() {
        File file = new File("assets\\bg_music.wav");
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(inputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            clip.start();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void playKeySwish() {
        new SoundServer(new File("assets\\swish.wav")).start();
    }

    public static void playKeyType() {

        new SoundServer(new File("assets\\keyType.wav")).start();
    }

    public static void playTap() {
        new SoundServer(new File("assets\\tap2.wav")).start();
    }

    public static void playAdd() {
        new SoundServer(new File("assets\\add.wav")).start();
    }

    public static void playError() {
        new SoundServer(new File("assets\\err.wav")).start();
    }
    public static void playChoose() {
        new SoundServer(new File("assets\\chooseDice.wav")).start();
    }
    public static void playRoll() {
        new SoundServer(new File("assets\\diceroll.wav")).start();
    }

    public static void setVolume(float volume) {
        Sound.volume = volume;
        backgroundControl.setValue(getVolume());
    }

    public static float getVolume() {
        return volume;
    }

}
