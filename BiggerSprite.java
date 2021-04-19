import java.awt.Graphics;

public class BiggerSprite extends Sprite {
    final private static int boundIncrease = 10;

    BiggerSprite(String imagePath) {
        super(imagePath);
    }
    
    //Image is bigger than hitbox to look less janky
    @Override public void drawSprite(Graphics g) {
        g.drawImage(getImage(), getX() - boundIncrease, getY() - boundIncrease, getWidth() + boundIncrease, getHeight() + boundIncrease, null);
    }
}
