//Sprite with generic movement
public class MovingSprite extends Sprite {
    protected boolean moving;
    protected double moveXRatio;
    protected double moveYRatio;
    protected double movePower;

    public MovingSprite(String imagePath, int x, int y, int height, int width) {
        super(imagePath, x, y, height, width);
        moving = false;
        moveXRatio = 0;
        moveYRatio = 0;
        movePower = 0;
    }

    public MovingSprite(String imagePath, int x, int y, int height, int width, double xRatio, double yRatio, double power) {
        super(imagePath);
        this.locationX = x;
        this.locationY = y;
        this.width = width;
        this.height = height;
        this.moving = true;
        this.moveXRatio = xRatio;
        this.moveYRatio = yRatio;
        this.movePower = power;
    }

    public MovingSprite(String imagePath, int x, int y, int height, int width, boolean moving, double xRatio, double yRatio, double power) {
        super(imagePath);
        this.locationX = x;
        this.locationY = y;
        this.width = width;
        this.height = height;
        this.moving = moving;
        this.moveXRatio = xRatio;
        this.moveYRatio = yRatio;
        this.movePower = power;
    }

    //Getters
    public boolean getMoving() { return moving; }
    public double getMoveXRatio() { return moveXRatio; }
    public double getMoveYRatio() { return moveYRatio; }
    public double getMovePower() { return movePower; }
    
    @Override public void updateState() {
        if(moving) {
            double multiplier;
            if (moveXRatio == 0 && moveYRatio == 0) {
                multiplier = 0;
            }
            else {
                multiplier =  movePower / ( Math.sqrt(Math.pow(moveXRatio, 2) + Math.pow(moveYRatio, 2)) );
            }
            setX(getX() + (int) (multiplier * moveXRatio * SpriteMover.speedupFactor));
            setY(getY() + (int) (multiplier * moveYRatio * SpriteMover.speedupFactor));
        }
    }
}
