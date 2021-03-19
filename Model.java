import java.awt.event.ActionEvent;
public class Model {
    private boolean paused;

    Model() {
        paused = false;
    }

    public void update() {
        System.out.println("Updating!");
    }

    public boolean isPaused() {
        return paused;
    }

}