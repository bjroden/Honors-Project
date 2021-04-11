import java.awt.event.*;

import javax.swing.JFileChooser;

import java.awt.Component.*;

public class Controller implements MouseListener, ActionListener {
    Model model;
    View view;

    Controller() {
        model = new Model();
        view = new View(this, model);
        Thread s = new Thread(new SpriteMover(this));
        s.start();
    }

    public void update() {
        model.update();
        view.repaint();
    }

    public boolean getPaused() {
        return model.paused;
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
    //Perform appropriate button action
    public void actionPerformed(ActionEvent e) {
        //TODO: implement
        boolean prevPaused = model.paused;
        final JFileChooser fc = new JFileChooser("./");
        switch(e.getActionCommand()) {
            case "Pause":
                model.paused = !model.paused;
                System.out.println("Pausing game");
                break;
            case "Save":
                System.out.println("Saving game");
                model.paused = true;
                if (fc.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
                    model.saveGame(fc.getSelectedFile());
                }
                model.paused = prevPaused;
                break;
            case "Load":
                System.out.println("Loading game");
                model.paused = true;
                if (fc.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
                    model.loadGame(fc.getSelectedFile());
                }
                model.paused = prevPaused;
                break;
            case "Instructions":
                model.paused = true;
                view.displayInstructions();
                model.paused = prevPaused;
                break;
            default:
                System.out.println("Default button action");
        }
        //TODO: check if this needs to be done or refactored
        view.repaint();
    }

    public static void main(String args[]) {
        new Controller();            
    }
}
