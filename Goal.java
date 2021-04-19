public class Goal extends BiggerSprite {
    Goal(int x, int y, int height, int width) {
        super("yellowSquare.png", x, y, height, width);
    }

    public void setReady() {
        setImage("greenSquare.png");
    }
}
