import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


class Sprite
{
	private String jpgName;
	protected int locationX;
	protected int locationY;
    protected int height;
    protected int width;
	private Image image;

	public Sprite(String jpgName)
	{
		setImage(jpgName);
		locationX = 0;
		locationY = 0;
        height = 200;
        width = 200;
	}
	
	public int getX() {	return locationX; }
	public int getY() {	return locationY; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
	public void setX(int x) { locationX = x; }
	public void setY(int y) { locationY = y; }
    public void setWidth(int w) { width = w; }
    public void setHeight(int h) { height = h; }
	
	public void setImage(String imagePath) {
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException ioe) {
            System.out.println("Unable to load image file.");
        }
	}
	public Image getImage() { return image; }	
	
	public void updateState() {

	}

	public void drawSprite(Graphics g) {
        g.drawImage(getImage(), getX(), getY(), getWidth(), getHeight(), null);
	}

	//TODO: double check that this is fixed
	public boolean overlaps(Sprite s) {
		boolean xOverlap = (this.getX() + this.getWidth() > s.getX() && s.getX() + s.getWidth() > this.getX());
		boolean yOverlap = (this.getY() + this.getHeight() > s.getY() && s.getY() + s.getHeight() > this.getY());
		return (xOverlap && yOverlap);
	}
}