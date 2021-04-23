public class Wall extends MovingObstacle {
    Wall(int x, int y, int height, int width) {
        super("blackSquare.png", x, y, height, width);
    }

    Wall(int x, int y, int height, int width, XorY XorY, int movePower, int loc1, int loc2) {
        super("blackSquare.png", x, y, height, width, XorY, movePower, loc1, loc2);
    }

    Wall(int x, int y, int height, int width, double xRatio, double yRatio, int movePower, int x1, int x2, int y1, int y2) {
        super("blackSquare.png", x, y, height, width, xRatio, yRatio, movePower, x1, x2, y1, y2);
    }
}
