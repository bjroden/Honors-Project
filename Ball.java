import java.awt.Graphics;

public class Ball extends MovingSprite {
    final private static int boundIncrease = 10;

    Ball(int x, int y) {
        super("ball.jpg", x, y, 40, 40);
    }

    Ball(int x, int y, int height, int width, boolean moving, double xRatio, double yRatio, int power) {
        super("ball.jpg", x, y, height, width, moving, xRatio, yRatio, power);
    }

    public void startMove(int mouseX, int mouseY) {
        int maxX = 18;
        int maxY = 18;

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
        if(locationX + width > Model.mapWidth || getCenterX() < 0) {
            moveXRatio *= -1;
        }
        if(locationY + height > Model.mapHeight || getCenterY() < 0) {
            moveYRatio *= -1;
        }
        super.updateState();
        if(movePower <= 0) {
            moving = false;
        }
        else {
            movePower -= 1;
        }
    }
}
