import java.awt.event.*;

public class Controller implements MouseListener {
    Model model;
    View view;

    Controller() {
        model = new Model();
        view = new View(this, model);
        Thread s = new Thread(new SpriteMover(view, model));
        s.start();
    }

    public void update() {
        model.update();
        view.repaint();
    }

    public static void main(String args[]) {
        new Controller();            
    }

    public void mouseEntered(MouseEvent e) {

    }
    public void mouseExited(MouseEvent e) {

    }
    public void mouseReleased(MouseEvent e) {

    }
    public void mousePressed(MouseEvent e) {

    }
    public void mouseClicked(MouseEvent e) {

    }
}
