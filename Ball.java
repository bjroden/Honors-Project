import java.awt.Graphics;

public class Ball extends MovingSprite {
    final private static int boundIncrease = 10;

    Ball(int x, int y) {
        super("ball.jpg", x, y, 60, 60);
    }

    Ball(int x, int y, int height, int width, boolean moving, double xRatio, double yRatio, int power) {
        super("ball.jpg", x, y, height, width, moving, xRatio, yRatio, power);
    }

    public void startMove(int mouseX, int mouseY) {
        int maxX = 25;
        int maxY = 25;

        int distX = getX() + (getWidth() / 2) - mouseX;
        int distY = getY() + (getHeight() / 2) - mouseY;

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
        //TODO: clean this up
        if(this.getX() > s.getX() && this.getX() < s.getX() + s.getWidth()) {
            moveYRatio *= -1;
        }
        if(this.getY() > s.getY() && this.getY() < s.getY() + s.getHeight()) {
            moveXRatio *= -1;
        }
        if(s.getX() > this.getX() && s.getX() < this.getX() + this.getWidth()) {
            moveYRatio *= -1;
        }
        if(s.getY() > this.getY() && s.getY() < this.getY() + this.getHeight()) {
            moveXRatio *= -1;
        }
        //TODO: Get stuck in walls less, probably wanna do something different
        super.updateState();
    }

    @Override public void updateState() {
        super.updateState();
        if(movePower <= 0) {
            moving = false;
        }
        else {
            movePower -= 2;
        }
    }

    //Image is bigger than hitbox to look less janky
    @Override public void drawSprite(Graphics g) {
        g.drawImage(getImage(), getX() - boundIncrease, getY() - boundIncrease, getWidth() + boundIncrease, getHeight() + boundIncrease, null);
    }
}
