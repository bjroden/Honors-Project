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
    private int startBallx;
    private int startBally;
    private int currentLevel;


    Model() {
        sprites = new ArrayList<Sprite>();
        paused = false;
        ballClicked = false;

        loadLevel(1);
    }

    public void saveGame(File file) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("Ball " + ball.getX() + " " + ball.getY() + " " + ball.getHeight() + " " + ball.getWidth() + " " + ball.getMoving() + " " + ball.getMoveXRatio() + " " + ball.getMoveYRatio() + " "  + ball.getMovePower() + "\n");
            
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
            String[] nums;
            Ball newBall;
            
            line = reader.readLine();
            nums = line.split("\\s+");
            if (nums.length == 0 || !nums[0].equals("Ball")) {
                throw new IOException("Error reading file: Ball not found");
            }
            else {
                //TODO: catch exception
                int ballX = Integer.parseInt(nums[1]);
                int ballY = Integer.parseInt(nums[2]);
                int ballHeight = Integer.parseInt(nums[3]);
                int ballWidth = Integer.parseInt(nums[4]);
                boolean ballMoving = Boolean.parseBoolean(nums[5]) ;
                double moveX = Double.parseDouble(nums[6]); 
                double moveY = Double.parseDouble(nums[7]); 
                int movePower = Integer.parseInt(nums[8]);

                newBall = new Ball(ballX, ballY, ballHeight, ballWidth, ballMoving, moveX, moveY, movePower);
            }

            //TODO: add other types
            while ((line = reader.readLine()) != null) {
                nums = line.split("\\s+");
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
            ball = newBall;
            sprites = newSprites;
        }
        catch(IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    public void update() {
        synchronized(sprites) {
            Iterator<Sprite> iter = sprites.iterator();
            while(iter.hasNext()) {
                iter.next().updateState();
            }
        }

        ball.updateState();

        synchronized(sprites) {
            Iterator<Sprite> iter = sprites.iterator();
            while(iter.hasNext()) {
                Sprite s = iter.next();
                if(ball.overlaps(s)) {
                    if(s instanceof RedZone) {
                        resetBall();
                    }
                }
            }
        }
    }

    private void resetBall() {
        ball = new Ball(startBallx, startBally);
    }

    public ArrayList<Sprite> getSprites() {
        return sprites;
    }

    public void setBallClicked(int xcoord, int ycoord) {
        boolean withinX = (xcoord > ball.getX() && xcoord < ball.getX() + ball.getWidth());
        boolean withinY = (ycoord > ball.getY() && ycoord < ball.getY() + ball.getHeight());
        ballClicked = (withinX && withinY);
    }

    public void ballReleased(int mouseX, int mouseY) {
        if(ballClicked) {
            ball.startMove(mouseX, mouseY);
        }
        ballClicked = false;
    }

    public boolean getBallClicked() {
        return ballClicked;
    }

    public Ball getBall() {
        return ball;
    }

    public void loadLevel(int level) {
        switch(level) {
            case 1:
                setLevel(1);
                startBallx = 200;
                startBally = 200;
                ball = new Ball(startBallx, startBally);
                sprites.add(new RedZone(300, 300, 200, 100));
                break;
            default:
                boolean prevPaused = paused;
                paused = true;
                JOptionPane.showMessageDialog(null, "Could not find level being loaded");
                paused = prevPaused;

        }
    }

    public void setLevel(int level) {
        currentLevel = level;
    }
}