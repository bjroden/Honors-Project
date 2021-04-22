//This class lets objects like targets and killzones move
public class MovingObstacle extends MovingSprite {
    public enum XorY {moveX, moveY};
    protected int x1;
    protected int x2;
    protected int y1;
    protected int y2;

    MovingObstacle(String imagePath, int x, int y, int height, int width) {
        super(imagePath, x, y, height, width);
    }

    //x is 0, y is 1
    MovingObstacle(String imagePath, int x, int y, int height, int width, XorY XorY, int movePower, int loc1, int loc2) {
        super(imagePath, x, y, height, width);
        moving = true;
        this.movePower = movePower;
        if(XorY == MovingObstacle.XorY.moveX) {
            x1 = loc1;
            x2 = loc2;
            moveXRatio = 1;
        }
        else {
            y1 = loc1;
            y2 = loc2;
            moveYRatio = 1;
        }
    }

    MovingObstacle(String imagePath, int x, int y, int height, int width, double xRatio, double yRatio, int movePower, int x1, int x2, int y1, int y2) {
        super(imagePath, x, y, height, width, xRatio, yRatio, movePower);
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    @Override public void updateState() {
        super.updateState();
        if (locationX < x1 || locationX > x2) {
            moveXRatio *= -1;
        }
        if (locationY < y1 || locationY > y2) {
            moveYRatio *= -1;
        }
    }
}
