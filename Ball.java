import java.awt.Graphics;

public class Ball extends MovingSprite {
    Ball(int x, int y) {
        super("images/ball.png", x, y, 40, 40);
    }

    Ball(int x, int y, int height, int width, boolean moving, double xRatio, double yRatio, double power) {
        super("images/ball.png", x, y, height, width, moving, xRatio, yRatio, power);
    }

    public void startMove(int mouseX, int mouseY) {
        int maxX = 19;
        int maxY = 19;

        int distX = (getX() + (getWidth() / 2) - mouseX) / 4;
        int distY = (getY() + (getHeight() / 2) - mouseY) / 4;

        if(Math.abs(distX) > maxX) {
            distX = maxX * (int) Math.signum(distX);
        }
        if(Math.abs(distY) > maxY) {
            distY = maxY * (int) Math.signum(distY);
        }

        double distance = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
        if (distance == 0) {
            moving = false;
        }
        else {
            moving = true;
            moveXRatio = distX / distance;
            moveYRatio = distY / distance;
            movePower = (int) distance;
        }
    }

    public void startMove(LaunchPad x) {
        LaunchPad.direction dir = x.getDirection();
        switch(dir) {
            case UP:
                moveXRatio = 0;
                moveYRatio = -1;
                break;
            case DOWN:
                moveXRatio = 0;
                moveYRatio = 1;
                break;
            case LEFT:
                moveXRatio = -1;
                moveYRatio = 0;
                break;
            case RIGHT:
                moveXRatio = 1;
                moveYRatio = 0;
                break;
            default:
                System.out.println("Startmove switch made impossible command");
        }
        moving = true;
        movePower = x.getLaunchPower();
    }

    public void bounce(Sprite s) {
        //TODO: clean this up, corners can bug out
        boolean bounced = false;
        if(this.getCenterX() > s.getX() && this.getCenterX() < s.getX() + s.getWidth()) {
            moveYRatio *= -1;
            bounced = true;
        }
        else if(this.getCenterY() > s.getY() && this.getY() < s.getCenterY() + s.getHeight()) {
            moveXRatio *= -1;
            bounced = true;
        }
        if (!bounced) {
            moveYRatio *= -1;
            moveXRatio *= -1;
        }

        //TODO: Get stuck in walls less, probably wanna do something different
        super.updateState();
    }

    @Override public void updateState() {
        //TODO:
        super.updateState();
        if(locationX + width > Model.mapWidth || getCenterX() < 0) {
            moveXRatio *= -1;
        }
        if(locationY + height > Model.mapHeight || getCenterY() < 0) {
            moveYRatio *= -1;
        }
        if(movePower <= 0) {
            moving = false;
        }
        else {
            movePower -= 1.0 * SpriteMover.speedupFactor;
        }
    }
}
