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
    private int numTargets;
    private int strokes;

    Model() {
        sprites = new ArrayList<Sprite>();
        paused = false;
        ballClicked = false;
        numTargets = 0;

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
                    else if(s instanceof Target) {
                        removeTarget(s);
                        //TODO: added to prevent concurrentModification exception, double check 
                        iter=sprites.iterator();
                    }
                    else if(s instanceof Goal) {
                        if(numTargets <= 0) {
                            loadLevel(currentLevel + 1);
                            return;
                        }
                    }
                    else if(s instanceof Wall) {
                        ball.bounce(s);
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
        if (!paused) {
            boolean withinX = (xcoord > ball.getX() && xcoord < ball.getX() + ball.getWidth());
            boolean withinY = (ycoord > ball.getY() && ycoord < ball.getY() + ball.getHeight());
            ballClicked = (withinX && withinY);
        }
    }

    public void ballReleased(int mouseX, int mouseY) {
        if(ballClicked) {
            ball.startMove(mouseX, mouseY);
            strokes++;
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
        paused = true;
        switch(level) {
            case 1:
                startBallx = 200;
                startBally = 200;
                synchronized(sprites) {
                    sprites.clear();
                    sprites.add(new Target(250, 250, 30, 30));
                    sprites.add(new Target(300, 300, 30, 30));
                    sprites.add(new Target(350, 250, 30, 30));
                    sprites.add(new Wall(100, 350, 75, 400));
                    sprites.add(new Goal(400, 150, 75, 100));
                }
                break;
            case 2:
                startBallx = 300;
                startBally = 600;
                synchronized(sprites) {
                    sprites.clear();
                    sprites.add(new Wall(200, 0, 800, 50));
                    sprites.add(new Wall(400, 0, 800, 50));
                    sprites.add(new RedZone(100, 300, 50, 50, MovingObstacle.XorY.moveX, 15, 100, 500));
                    sprites.add(new Goal(300, 200, 50, 50));
                }
                break;
            case 999:
                JOptionPane.showMessageDialog(null, "Level 1");
                startBallx = 200;
                startBally = 400;
                synchronized(sprites) {
                    sprites.clear();
                    sprites.add(new Target(40, 40, 50, 40));
                    sprites.add(new Target(400, 300, 50, 50, MovingObstacle.XorY.moveY, 20, 100, 500));
                    sprites.add(new RedZone(100, 500, 100, 100, MovingObstacle.XorY.moveX, 5, 100, 200));
                    sprites.add(new Target(100, 100, 100, 100, 1, 0, 10, 40, 300, 0, 0));
                    sprites.add(new Goal(500, 100, 50, 50));
                    sprites.add(new Wall(10, 200, 100, 250));
                }
                break;
            case 1000:
                JOptionPane.showMessageDialog(null, "Level 2");
                startBallx = 200;
                startBally = 200;
                synchronized(sprites) {
                    sprites.clear();
                    sprites.add(new Wall(300, 300, 600, 50));
                    sprites.add(new Goal(100, 100, 50, 100));
                }
                break;
            default:
                JOptionPane.showMessageDialog(null, "Could not find level being loaded");
                return;
        }
        currentLevel = level;
        ball = new Ball(startBallx, startBally);
        setNumTargets();
        paused = false;
    }

    public void removeTarget(Sprite s) {
        sprites.remove(s);
        numTargets -= 1;
        if (numTargets <= 0) {
            readyGoals();
        }
    }

    public void setNumTargets() {
        int newNumTargets = 0;
        Iterator<Sprite> iter = sprites.iterator();
        while(iter.hasNext()) {
            if(iter.next() instanceof Target) {
                newNumTargets++;
            }
        }
        numTargets = newNumTargets;
        if (numTargets <= 0) {
            readyGoals();
        }
    }

    public void readyGoals() {
        Iterator<Sprite> iter = sprites.iterator();
        while(iter.hasNext()) {
            Sprite x = iter.next();
            if(x instanceof Goal) {
                ((Goal) x).setReady();
            }
        }
    }

    public int getStrokes() {
        return strokes;
    }
}