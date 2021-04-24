import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class LaunchPad extends Sprite {
    public enum direction {UP, DOWN, LEFT, RIGHT};
    private direction dir;
    private int power;

    //TODO: rotate image
    LaunchPad(int x, int y, int height, int width, direction dir, int power) {
        super("arrow.png", x, y, height, width);
        this.dir = dir; 
        this.power = power;
    }

    public direction getDirection() {
        return dir;
    }

    public int getMovePower() {
        return power;
    }
}
