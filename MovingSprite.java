//Sprite with generic movement
public class MovingSprite extends Sprite {
    protected boolean moving;
    protected double moveDirection;
    protected double movePower;

    public MovingSprite(String imagePath, int x, int y, int height, int width) {
        super(imagePath, x, y, height, width);
        moving = false;
        moveDirection = Math.toRadians(0);
        movePower = 0;
    }

    public MovingSprite(String imagePath, int x, int y, int height, int width, double moveDirection, double power) {
        super(imagePath);
        this.locationX = x;
        this.locationY = y;
        this.width = width;
        this.height = height;
        this.moving = true;
        this.moveDirection = moveDirection;
        this.movePower = power;
    }

    public MovingSprite(String imagePath, int x, int y, int height, int width, boolean moving, double moveDirection, double power) {
        super(imagePath);
        this.locationX = x;
        this.locationY = y;
        this.width = width;
        this.height = height;
        this.moving = moving;
        this.moveDirection = moveDirection;
        this.movePower = power;
    }

    //Getters
    public boolean getMoving() { return moving; }
    public double getMoveDirection() { return moveDirection; }
    public double getMovePower() { return movePower; }
    
    @Override public void updateState() {
        if(moving) {
            while (moveDirection > 2 * Math.PI) {
                moveDirection -= 2 * Math.PI;
            }
            while (moveDirection < 0) {
                moveDirection += 2 * Math.PI;
            }
            setX(getX() + (int) (movePower * Math.cos(moveDirection) * SpriteMover.speedupFactor));
            setY(getY() - (int) (movePower * Math.sin(moveDirection) * SpriteMover.speedupFactor));
        }
    }
}
