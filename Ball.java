public class Ball extends MovingSprite {
    Ball(int x, int y) {
        super("images/ball.png", x, y, 40, 40);
    }

    Ball(int x, int y, int height, int width, boolean moving, double moveDirection, double power) {
        super("images/ball.png", x, y, height, width, moving, moveDirection, power);
    }

    //Get mouse coordinates and determine move direction and power
    public void startMove(int mouseX, int mouseY) {
        final int maxSpeed = 26;

        int distX = getX() + (getWidth() / 2) - mouseX;
        int distY = getY() + (getHeight() / 2) - mouseY;
        double distance = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));

        moving = true;
        moveDirection = Math.atan2(mouseY - getY(), getCenterX() - mouseX);
        if(distance > maxSpeed * 4) {
            movePower = 26;
        }
        else {
            movePower = distance / 4;
        }
    }

    //Start move from a launchPad
    public void startMove(LaunchPad x) {
        LaunchPad.direction dir = x.getDirection();
        switch(dir) {
            case UP:
                moveDirection = Math.toRadians(90);
                break;
            case DOWN:
                moveDirection = Math.toRadians(270);
                break;
            case LEFT:
                moveDirection = Math.toRadians(180);
                break;
            case RIGHT:
                moveDirection = Math.toRadians(0);
                break;
            default:
                System.out.println("Startmove switch made impossible command");
        }
        moving = true;
        movePower = x.getLaunchPower();
    }

    public void bounce(Sprite s) {
        boolean bounced = false;
        if(this.getCenterX() > s.getX() && this.getCenterX() < s.getX() + s.getWidth()) {
            moveDirection = 2 * Math.PI - moveDirection;
            bounced = true;
        }
        else if(this.getCenterY() > s.getY() && this.getY() < s.getCenterY() + s.getHeight()) {
            moveDirection = Math.PI - moveDirection;
            bounced = true;
        }
        if (!bounced) {
            moveDirection += Math.toRadians(180);
        }

        //Move 1 tick to get stuck in walls less
        super.updateState();
    }

    @Override public void updateState() {
        super.updateState();
        if(locationX + width > Model.mapWidth || getCenterX() < 0) {
            moveDirection = Math.PI - moveDirection;
        }
        if(locationY + height > Model.mapHeight || getCenterY() < 0) {
            moveDirection = 2 * Math.PI - moveDirection;
        }
        if(movePower <= 0) {
            moving = false;
        }
        else {
            movePower -= 1.0 * SpriteMover.speedupFactor;
        }
    }
}
