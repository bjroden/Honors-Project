public class RedZone extends MovingObstacle {
    RedZone(int x, int y, int height, int width) {
        super("redSquare.png", x, y, height, width);
    }

    RedZone(int x, int y, int height, int width, XorY XorY, int movePower, int loc1, int loc2) {
        super("redSquare.png", x, y, height, width, XorY, movePower, loc1, loc2);
    }

    RedZone(int x, int y, int height, int width, double xRatio, double yRatio, int movePower, int x1, int x2, int y1, int y2) {
        super("redSquare.png", x, y, height, width, xRatio, yRatio, movePower, x1, x2, y1, y2);
    }
    RedZone(int x, int y, int height, int width, boolean moving, double xRatio, double yRatio, int movePower, int x1, int x2, int y1, int y2) {
        super("redSquare.png", x, y, height, width, moving, xRatio, yRatio, movePower, x1, x2, y1, y2);
    }
}
