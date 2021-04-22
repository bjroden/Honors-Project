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
        int maxX = 20;
        int maxY = 20;

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
