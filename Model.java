import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import java.io.*;
import java.awt.Point;

public class Model {
    private ArrayList<Sprite> sprites;
    private Ball ball;
    private boolean ballClicked;
    public boolean paused;

    Model() {
        sprites = new ArrayList<Sprite>();
        paused = false;
        ballClicked = false;
        ball = new Ball();
    }

    public void saveGame(File file) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            synchronized(sprites) {
                Iterator<Sprite> iter = sprites.iterator();
                while(iter.hasNext()) {
                    Sprite x = iter.next();
                    //TODO: add other types
                    writer.write("Test " + x.getX() + " " + x.getY() + " " + x.getHeight() + " " + x.getWidth() + "\n");
                }
            }
        }
        catch(IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void loadGame(File file) {
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            ArrayList<Sprite> newSprites = new ArrayList<Sprite>();
            String line;
            //TODO: add other types
            while ((line = reader.readLine()) != null) {
                String[] nums = line.split("\\s+");
                switch(nums[0]) {
                    case "Test":
                        test spriteToAdd = new test();
                        spriteToAdd.setX(Integer.parseInt(nums[1]));
                        spriteToAdd.setY(Integer.parseInt(nums[2]));
                        spriteToAdd.setHeight(Integer.parseInt(nums[3]));
                        spriteToAdd.setWidth(Integer.parseInt(nums[4]));
                        newSprites.add(spriteToAdd);
                        break;
                    default:
                        throw new IOException("Error reading file contents");
                }
            }
            //TODO: check more first
            sprites = newSprites;
        }
        catch(IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    public void update() {
        Iterator<Sprite> iter = sprites.iterator();
        while(iter.hasNext()) {
            //TODO: do something else with this
            Sprite x = iter.next();
            x.setX(x.getX() + 1);
            x.setY(x.getY() + 1);
        }
    }

    public ArrayList<Sprite> getSprites() {
        return sprites;
    }

    public void setBallClicked(Point info) {
        boolean withinX = (info.getX() > ball.getX() && info.getX() < ball.getX() + ball.getWidth());
        boolean withinY = (info.getY() > ball.getY() && info.getY() < ball.getY() + ball.getHeight());
        ballClicked = (withinX && withinY);
    }

    public void ballReleased() {
        //TODO:
        ballClicked = false;
    }

    public boolean getBallClicked() {
        return ballClicked;
    }

    public Ball getBall() {
        return ball;
    }
}