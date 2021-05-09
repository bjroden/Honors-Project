//Red zones that reset player to starting position
public class RedZone extends MovingObstacle {
    RedZone(int x, int y, int height, int width) {
        super("images/redSquare.png", x, y, height, width);
    }

    RedZone(int x, int y, int height, int width, XorY XorY, double movePower, int loc1, int loc2) {
        super("images/redSquare.png", x, y, height, width, XorY, movePower, loc1, loc2);
    }

    RedZone(int x, int y, int height, int width, double moveDirection, double movePower, int x1, int x2, int y1, int y2) {
        super("images/redSquare.png", x, y, height, width, moveDirection, movePower, x1, x2, y1, y2);
    }
    RedZone(int x, int y, int height, int width, boolean moving, double moveDirection, double movePower, int x1, int x2, int y1, int y2) {
        super("images/redSquare.png", x, y, height, width, moving, moveDirection, movePower, x1, x2, y1, y2);
    }
}
