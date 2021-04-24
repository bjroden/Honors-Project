public class Target extends MovingObstacle {
    Target(int x, int y, int height, int width) {
        super("blueSquare.png", x, y, height, width);
    }

    Target(int x, int y, int height, int width, XorY XorY, int movePower, int loc1, int loc2) {
        super("blueSquare.png", x, y, height, width, XorY, movePower, loc1, loc2);
    }

    Target(int x, int y, int height, int width, double xRatio, double yRatio, int movePower, int x1, int x2, int y1, int y2) {
        super("blueSquare.png", x, y, height, width, xRatio, yRatio, movePower, x1, x2, y1, y2);
    }
    Target(int x, int y, int height, int width, boolean moving, double xRatio, double yRatio, int movePower, int x1, int x2, int y1, int y2) {
        super("blueSquare.png", x, y, height, width, moving, xRatio, yRatio, movePower, x1, x2, y1, y2);
    }
}
