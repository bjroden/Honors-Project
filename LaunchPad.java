public class LaunchPad extends MovingObstacle {
    public enum direction {UP, DOWN, LEFT, RIGHT};
    private direction dir;
    private int launchPower;

    //TODO: rotate image
    LaunchPad(int x, int y, int height, int width, direction dir, int lPow) {
        super(selectImage(dir), x, y, height, width);
        this.dir = dir; 
        this.launchPower = lPow;
    }

    LaunchPad(int x, int y, int height, int width, XorY XorY, double movePower, int loc1, int loc2, direction dir, int lPow) {
        super(selectImage(dir), x, y, height, width, XorY, movePower, loc1, loc2);
        this.dir = dir; 
        this.launchPower = lPow;
    }

    LaunchPad(int x, int y, int height, int width, double xRatio, double yRatio, double movePower, int x1, int x2, int y1, int y2, direction dir, int lPow) {
        super(selectImage(dir), x, y, height, width, xRatio, yRatio, movePower, x1, x2, y1, y2);
        this.dir = dir; 
        this.launchPower = lPow;
    }

    LaunchPad(int x, int y, int height, int width, boolean moving, double xRatio, double yRatio, double movePower, int x1, int x2, int y1, int y2, direction dir, int lPow) {
        super(selectImage(dir), x, y, height, width, moving, xRatio, yRatio, movePower, x1, x2, y1, y2);
        this.dir = dir;
        this.launchPower = lPow;
    }

    public direction getDirection() {
        return dir;
    }

    public int getLaunchPower() {
        return launchPower;
    }

    private static String selectImage(direction dir) {
        String returnString = null;
        switch(dir) {
            case UP:
                returnString = "images/upArrow.png";
                break;
            case RIGHT:
                returnString = "images/rightArrow.png";
                break;
            case LEFT:
                returnString = "images/leftArrow.png";
                break;
            case DOWN:
                returnString = "images/downArrow.png";
                break;
        }
        return returnString;
    }
}
