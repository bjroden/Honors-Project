import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import java.io.*;
import java.awt.Point;

public class Model {
    final public static int mapWidth = 800;
    final public static int mapHeight = 800;

    SoundPlayer soundplayer;
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

        soundplayer = new SoundPlayer();

        loadLevel(4);
    }

    public void saveGame(File file) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            //Write model
            writer.write(String.format("Model %b %d %d %d %d %d\n", paused, startBallx, startBally, currentLevel, numTargets, strokes));
            //Write ball
            writer.write(String.format("Ball %d %d %d %d %b %f %f %f\n", ball.getX(), ball.getY(), ball.getHeight(), ball.getWidth(), ball.getMoving(), ball.getMoveXRatio(), ball.getMoveYRatio(), ball.getMovePower()));
            
            //Write sprites
            synchronized(sprites) {
                Iterator<Sprite> iter = sprites.iterator();
                while(iter.hasNext()) {
                    Sprite x = iter.next();
                    writer.write(String.format("%s %d %d %d %d", x.getClass().getName(), x.getX(), x.getY(), x.getHeight(), x.getWidth()));
                    if (x instanceof MovingObstacle) {
                        MovingObstacle o = ((MovingObstacle) x);
                        writer.write(String.format(" %b %f %f %f %d %d %d %d", o.getMoving(), o.getMoveXRatio(), o.getMoveYRatio(), o.getMovePower(), o.getX1(), o.getX2(), o.getY1(), o.getY2()));
                    }
                    if (x instanceof LaunchPad) {
                        LaunchPad l = (LaunchPad) x;
                        writer.write(String.format(" %s %d", l.getDirection().name(), l.getLaunchPower()));
                    }
                    writer.write("\n");
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
            boolean newPaused;
            int newStartX, newStarty, newCurrentLevel, newNumTargets, newStrokes;

            //Read Model
            line = reader.readLine();
            nums = line.split("\\s+");
            if(nums.length == 0 || !nums[0].equals("Model")) {
                throw new IOException("Error reading file: Model data not found");
            }
            else {
                newPaused = Boolean.parseBoolean(nums[1]);
                newStartX = Integer.parseInt(nums[2]);
                newStarty = Integer.parseInt(nums[3]);
                newCurrentLevel = Integer.parseInt(nums[4]);
                newNumTargets = Integer.parseInt(nums[5]);
                newStrokes = Integer.parseInt(nums[6]);
            }
            
            //Read ball
            line = reader.readLine();
            nums = line.split("\\s+");
            if (nums.length == 0 || !nums[0].equals("Ball")) {
                throw new IOException("Error reading file: Ball not found");
            }
            else {
                int ballX = Integer.parseInt(nums[1]);
                int ballY = Integer.parseInt(nums[2]);
                int ballHeight = Integer.parseInt(nums[3]);
                int ballWidth = Integer.parseInt(nums[4]);
                boolean ballMoving = Boolean.parseBoolean(nums[5]) ;
                double moveX = Double.parseDouble(nums[6]); 
                double moveY = Double.parseDouble(nums[7]); 
                double movePower = Double.parseDouble(nums[8]);

                newBall = new Ball(ballX, ballY, ballHeight, ballWidth, ballMoving, moveX, moveY, movePower);
            }

            //Read sprites
            while ((line = reader.readLine()) != null) {
                //TODO: initialize different?
                boolean mov = false;;
                int x = 0, y, h = 0, w = 0, x1 = 0, x2 = 0, y1 = 0, y2 = 0, lPow = 0;
                double xR = 0, yR = 0, pow = 0;
                LaunchPad.direction dir = LaunchPad.direction.UP;

                nums = line.split("\\s+");
                x = Integer.parseInt(nums[1]);
                y = Integer.parseInt(nums[2]);
                h = Integer.parseInt(nums[3]);
                w = Integer.parseInt(nums[4]);
                if(nums[0].equals("RedZone") || nums[0].equals("Target") || nums[0].equals("LaunchPad")) {
                    mov = Boolean.parseBoolean(nums[5]);
                    xR = Double.parseDouble(nums[6]);
                    yR = Double.parseDouble(nums[7]);
                    pow = Double.parseDouble(nums[8]);
                    x1 = Integer.parseInt(nums[9]);
                    x2 = Integer.parseInt(nums[10]);
                    y1 = Integer.parseInt(nums[11]);
                    y2 = Integer.parseInt(nums[12]);
                }
                if(nums[0].equals("LaunchPad")) {
                    dir = LaunchPad.direction.valueOf(nums[13]);
                    lPow = Integer.parseInt(nums[14]);
                }
                switch(nums[0]) {
                    case "Test":
                        test spriteToAdd = new test();
                        spriteToAdd.setX(Integer.parseInt(nums[1]));
                        spriteToAdd.setY(Integer.parseInt(nums[2]));
                        spriteToAdd.setHeight(Integer.parseInt(nums[3]));
                        spriteToAdd.setWidth(Integer.parseInt(nums[4]));
                        newSprites.add(spriteToAdd);
                        break;
                    case "RedZone":
                        newSprites.add(new RedZone(x, y, h, w, mov, xR, yR, pow, x1, x2, y1, y2));
                        break;
                    case "Target":
                        newSprites.add(new Target(x, y, h, w, mov, xR, yR, pow, x1, x2, y1, y2));
                        break;
                    case "LaunchPad":
                        newSprites.add(new LaunchPad(x, y, h, w, mov, xR, yR, pow, x1, x2, y1, y2, dir, lPow));
                        break;
                    case "Goal":
                        newSprites.add(new Goal(x, y, h, w));
                        break;
                    case "Wall":
                        newSprites.add(new Wall(x, y, h, w));
                        break;
                    default:
                        throw new IOException("Error reading file contents");
                }
            }
            //Initialize model
            paused = newPaused;
            startBallx = newStartX;
            startBally = newStarty;
            currentLevel = newCurrentLevel;
            numTargets = newNumTargets;
            strokes = newStrokes;

            //Set new objects
            ball = newBall;
            sprites = newSprites;

            if(numTargets <= 0) {
                readyGoals();
            }
        }
        catch(IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: Illegal numbers found in save file.");
        }
        catch(ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "Error: Unidentified object found in save file.");
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error reading save file.");
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
                        soundplayer.playSound(SoundPlayer.sound.RESET);
                    }
                    else if(s instanceof Target) {
                        removeTarget(s);
                        //TODO: added to prevent concurrentModification exception, double check 
                        soundplayer.playSound(SoundPlayer.sound.TARGET);
                        iter=sprites.iterator();
                    }
                    else if(s instanceof Goal) {
                        if(numTargets <= 0) {
                            soundplayer.playSound(SoundPlayer.sound.GOAL);
                            loadLevel(currentLevel + 1);
                            return;
                        }
                    }
                    else if(s instanceof Wall) {
                        ball.bounce(s);
                        soundplayer.playSound(SoundPlayer.sound.BOUNCE);
                    }
                    else if(s instanceof LaunchPad) {
                        ball.startMove(((LaunchPad) s));
                        soundplayer.playSound(SoundPlayer.sound.LAUNCH);
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
                startBally = 550;
                synchronized(sprites) {
                    sprites.clear();
                    sprites.add(new Wall(200, 0, 800, 50));
                    sprites.add(new Wall(400, 0, 800, 50));
                    sprites.add(new RedZone(100, 200, 50, 50, MovingObstacle.XorY.moveX, 10, 100, 500));
                    sprites.add(new RedZone(500, 400, 50, 50, MovingObstacle.XorY.moveX, 10, 100, 500));
                    sprites.add(new Goal(300, 100, 50, 50));
                }
                break;
            case 3:
                startBallx = 325;
                startBally = 500;
                synchronized(sprites) {
                    sprites.clear();
                    sprites.add(new Wall(200, 380, 50, 300));
                    sprites.add(new Wall(0, 180, 620, 50));
                    sprites.add(new Wall(25, 550, 250, 625));
                    sprites.add(new Wall(200, 200, 200, 75));
                    sprites.add(new Wall(650, 180, 600, 150));
                    sprites.add(new Wall(200, 180, 50, 300));
                    sprites.add(new Wall(0, 0, 180, 800));
                    sprites.add(new RedZone(450, 550, 25, 25, MovingObstacle.XorY.moveY, 5, 400, 550));
                    sprites.add(new RedZone(450, 200, 25, 25, MovingObstacle.XorY.moveY, 7, 200, 400));
                    sprites.add(new Target(450, 400, 30, 30, MovingObstacle.XorY.moveX, 15, 450, 700));
                    sprites.add(new Target(125, 500, 30, 30));
                    sprites.add(new Target(150, 300, 30, 30));
                    sprites.add(new Target(225, 450, 30, 30));
                    sprites.add(new Target(75, 225, 30, 30));
                    sprites.add(new Goal(285, 240, 140, 20));
                    sprites.add(new LaunchPad(50, 155, 50, 150, LaunchPad.direction.DOWN, 25));
                    sprites.add(new LaunchPad(625, 225, 150, 50, LaunchPad.direction.LEFT, 35));
                }
                break;
            case 4:
                startBallx = 525;
                startBally = 500;
                synchronized(sprites) {
                    sprites.clear();
                    sprites.add(new Wall(625, 300, 300, 75));
                    sprites.add(new Target(575, 425, 30, 30));
                    sprites.add(new Wall(425, 300, 60, 200));
                    sprites.add(new Target(525, 375, 30, 30));
                    sprites.add(new LaunchPad(425, 400, 50, 50, LaunchPad.direction.LEFT, 35));
                    sprites.add(new Target(0, 400, 30, 30));
                    sprites.add(new LaunchPad(175, 500, 50, 100, LaunchPad.direction.UP, 25));
                    sprites.add(new RedZone(75, 300, 50, 40, MovingObstacle.XorY.moveX, 10, 75, 300));
                    sprites.add(new Wall(225, 25, 60, 200));
                    sprites.add(new Target(300, 90, 30, 30));
                    sprites.add(new Wall(325, 200, 60, 200));
                    sprites.add(new Target(350, 160, 30, 30));
                    sprites.add(new LaunchPad(430, 110, 50, 50, LaunchPad.direction.RIGHT, 25));
                    sprites.add(new RedZone(650, 0, 50, 40, MovingObstacle.XorY.moveY, 7, 0, 300));
                    sprites.add(new Goal(750, 0, 200, 50));
                }
                break;
            case 5:
                startBallx = 250;
                startBally = 100;
                synchronized(sprites) {
                    sprites.clear();
                    sprites.add(new LaunchPad(0, 50, 100, 30, MovingObstacle.XorY.moveX, 5, 0, 200, LaunchPad.direction.DOWN, 30));
                    sprites.add(new RedZone(0, 400, 50, 125));
                    sprites.add(new RedZone(450, 0, 400, 50));
                    sprites.add(new RedZone(200, 400, 50, 300));
                    sprites.add(new LaunchPad(300, 550, 50, 50, MovingObstacle.XorY.moveY, 10, 500, 700, LaunchPad.direction.RIGHT, 25));
                    sprites.add(new Target(375, 530, 40, 40));
                    sprites.add(new Target(475, 530, 40, 40));
                    sprites.add(new Target(575, 530, 40, 40));
                    sprites.add(new Wall(510, 300, 200, 40));
                    sprites.add(new Wall(625, 400, 40, 200));
                    sprites.add(new RedZone(550, 50, 25, 25, MovingObstacle.XorY.moveY, 15, 50, 450));
                    sprites.add(new LaunchPad(630, 300, 80, 40, LaunchPad.direction.UP, 15));
                    sprites.add(new LaunchPad(500, 50, 200, 20, LaunchPad.direction.RIGHT, 30));
                    sprites.add(new Target(530, 100, 40, 30));
                    sprites.add(new Wall(675, 200, 40, 200));
                    sprites.add(new Goal(750, 0, 200, 50));
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
        if(level != 1 && currentLevel != level) {
            JOptionPane.showMessageDialog(null, "Strokes: " + strokes);
        }
        currentLevel = level;
        strokes = 0;
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

    public int getLevel() {
        return currentLevel;
    }
}