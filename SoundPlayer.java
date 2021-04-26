import javax.swing.JOptionPane;
import java.io.File;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

//Sound manager for specific clips
public class SoundPlayer implements LineListener {
    public enum sound {BOUNCE, TARGET, RESET, GOAL, LAUNCH};

    private Clip bounceClip;
    private Clip targetClip;
    private Clip resetClip;
    private Clip goalClip;
    private Clip launchClip;
    private boolean launchPlaying;

    public SoundPlayer() {
        bounceClip = loadSound("sounds/bump1.wav");
        targetClip = loadSound("sounds/chord.wav");
        goalClip = loadSound("sounds/tada.wav");
        resetClip = loadSound("sounds/file.wav");
        launchClip = loadSound("sounds/jump1.wav");

        if (launchClip != null) {
            launchClip.addLineListener(this);
        }
        launchPlaying = false;
    }

    //Launchpad sfx must complete before playing again
    public void update(LineEvent event) {
        if(event.getType().equals(LineEvent.Type.STOP)) {
            launchPlaying = false;
        }
    }

    //Play specified sound
    public void playSound(sound s) {
        Clip playClip = null;
            switch(s) {
                case BOUNCE:
                    playClip = bounceClip;
                    break;
                case TARGET:
                    playClip = targetClip;
                    break;
                case RESET:
                    playClip = resetClip;
                    break;
                case GOAL:
                    playClip = goalClip;
                    break;
                case LAUNCH:
                    playClip = launchClip;
                    break;
                default:
            }
        if(playClip != null) {
            //Launchpad sfx must complete before playing again
            if(s == sound.LAUNCH) {
                if(!launchPlaying) {
                    playClip.stop();
                    playClip.setMicrosecondPosition(0);
                    playClip.start();
                    launchPlaying = true;
                }
            }
            else {
                playClip.stop();
                playClip.setMicrosecondPosition(0);
                playClip.start();
            }
        }
    }

    //Initial audio loads
    private Clip loadSound(String sound) {
        Clip clip = null;
        try {
            AudioInputStream aStream = AudioSystem.getAudioInputStream(new File(sound).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(aStream);
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading audio file: " + sound);
        }
        return clip;
    }
}
