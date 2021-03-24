import java.awt.event.ActionEvent;
public class Model {
    public boolean paused;

    Model() {
        paused = false;
    }

    public void update() {
        System.out.println("Updating!");
    }

}