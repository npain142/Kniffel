package logic;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Ein Soundserver ist ein Thread, der das oeffnen, starten und schließen
 * von Audiostreams verwaltet. Ein Thread eignet sich gut, da es manchmal
 * vorkommt, dass manche toene gleichzietig oder sehr schnell hintereinander
 * gespielt werden. Außerdem implementiert die Klasse einen LineListener,
 * welcher einfach anomalien eines existierenden Ausgabstroms abhört,
 * z.B. start, stop, close, open.
 */
public class SoundServer extends Thread implements LineListener {
    private static File file;
    private Clip clip;
    private AudioInputStream stream;
    private FloatControl vol;
    public SoundServer(File input) {
        file = input;
    }

    @Override
    public void run() {
        try {
            stream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(stream);
            vol = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            vol.setValue(Sound.getVolume());
            clip.addLineListener(this);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param event a line event that describes the change
     */
    @Override
    public void update(LineEvent event) {
        if (event.getType() == LineEvent.Type.STOP) {
            try {
                clip.close();
                stream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.interrupt();
        }
    }


}

