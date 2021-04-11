import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;

public class Model {
    private ArrayList<Sprite> sprites;
    public boolean paused;

    Model() {
        sprites = new ArrayList<Sprite>();
        //TODO: Remove this later
        sprites.add(new test());
        paused = false;
    }

    public void saveGame(File file) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            synchronized(sprites) {
                Iterator<Sprite> iter = sprites.iterator();
                while(iter.hasNext()) {
                    Sprite x = iter.next();
                    //TODO: replace with more fleshed out code
                    writer.write("nostalgiaCriticDevil.jpg, " + x.getX() + " " + x.getY() + " " + x.getHeight() + " " + x.getWidth() + "\n");
                }
            }
        }
        catch(IOException e) {
            System.out.println("File not found");
        }
    }
    
    public void loadGame(File file) {
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            synchronized(sprites) {
                sprites.clear();
                //TODO: Replace with more fleshed out code
                String sprite = reader.readLine();
                sprites.add(new test());
                String[] nums = sprite.split("\\s+");
                sprites.get(0).setX(Integer.parseInt(nums[1]));
                sprites.get(0).setY(Integer.parseInt(nums[2]));
                sprites.get(0).setHeight(Integer.parseInt(nums[3]));
                sprites.get(0).setWidth(Integer.parseInt(nums[4]));
            }
        }
        catch(IOException e) {
            System.out.println("File not found");
        }

    }

    public void update() {
        Iterator<Sprite> iter = sprites.iterator();
        while(iter.hasNext()) {
            //TODO: do something else with this
            Sprite x = iter.next();
            x.setX(x.getX() + 1);
            x.setY(x.getY() + 1);
        }
    }

    public ArrayList<Sprite> getSprites() {
        return sprites;
    }
}