//This class adds movement bounds for objects like targets and redzones
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
    MovingObstacle(String imagePath, int x, int y, int height, int width, XorY XorY, double movePower, int loc1, int loc2) {
        super(imagePath, x, y, height, width);
        moving = true;
        this.movePower = movePower;
        if(XorY == MovingObstacle.XorY.moveX) {
            x1 = loc1;
            x2 = loc2;
            y2 = Model.mapHeight;
            moveDirection = Math.toRadians(0);
        }
        else {
            y1 = loc1;
            y2 = loc2;
            x2 = Model.mapWidth;
            moveDirection = Math.toRadians(90);
        }
    }

    MovingObstacle(String imagePath, int x, int y, int height, int width, double moveDirection, double movePower, int x1, int x2, int y1, int y2) {
        super(imagePath, x, y, height, width, moveDirection, movePower);
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    MovingObstacle(String imagePath, int x, int y, int height, int width, boolean moving, double moveDirection, double movePower, int x1, int x2, int y1, int y2) {
        super(imagePath, x, y, height, width, moving, moveDirection, movePower);
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    //Getters
    public int getX1() { return x1; }
    public int getX2() { return x2; }
    public int getY1() { return y1; }
    public int getY2() { return y2; }

    //Move + change direction
    @Override public void updateState() {
        super.updateState();
        if (moving) {
            if (locationX < x1 || locationX > x2) {
                moveDirection = Math.PI - moveDirection;
            }
            if (locationY < y1 || locationY > y2) {
                moveDirection = 2 * Math.PI - moveDirection;
            }
        }
    }
}
