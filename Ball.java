public class Ball extends Sprite {
    private boolean moving;
    private double moveXRatio;
    private double moveYRatio;
    private int movePower;

    Ball() {
        super("ball.jpg");
        setX(200);
        setY(200);
        setWidth(100);
        setHeight(100);

        moving = false;
        moveXRatio = 0;
        moveYRatio = 0;
        movePower = 0;
    }

    public void startMove() {
        //TODO: more complex logic
        moving = true;
        moveXRatio = 1;
        moveYRatio = 1;
        movePower = 20;
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
