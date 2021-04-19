public class Ball extends Sprite {
    private boolean moving;
    private double moveXRatio;
    private double moveYRatio;
    private int movePower;

    Ball(int x, int y) {
        super("ball.jpg");
        setX(x);
        setY(y);
        setWidth(60);
        setHeight(60);

        moving = false;
        moveXRatio = 0;
        moveYRatio = 0;
        movePower = 0;
    }

    Ball(int x, int y, int height, int width,  boolean moving, double xRatio, double yRatio, int power) {
        super("ball.jpg");

        this.locationX = x;
        this.locationY = y;
        this.width = width;
        this.height = height;
        this.moving = moving;
        this.moveXRatio = xRatio;
        this.moveYRatio = yRatio;
        this.movePower = power;
    }

    public boolean getMoving() { return moving; }
    public double getMoveXRatio() { return moveXRatio; }
    public double getMoveYRatio() { return moveYRatio; }
    public int getMovePower() { return movePower; }

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
        if(moving) {
            double multiplier;
            //TODO: more complex logic
            if (moveXRatio == 0 && moveYRatio == 0) {
                multiplier = 0;
            }
            else {
                multiplier =  movePower / ( Math.sqrt(Math.pow(moveXRatio, 2) + Math.pow(moveYRatio, 2)) );
            }
            setX(getX() + (int) (multiplier * moveXRatio));
            setY(getY() + (int) (multiplier * moveYRatio));
            movePower -= 2;
        }

        if (movePower <= 0) {
            moving = false;
        }
    }
}
