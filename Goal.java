//End of level
public class Goal extends BiggerSprite {
    Goal(int x, int y, int height, int width) {
        super("images/yellowSquare.png", x, y, height, width);
    }

    //Set goals to green
    public void setReady() {
        setImage("images/greenSquare.png");
    }
}
