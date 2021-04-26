import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;

//User inputs
public class Controller implements MouseListener, KeyListener, ActionListener {
    Model model;
    View view;

    Controller() {
        model = new Model();
        view = new View(this, model);
        Thread s = new Thread(new SpriteMover(this));
        s.start();
    }

    //Called by SpriteMover to advance game 1 tick
    public void update() {
        model.update();
        view.repaint();
    }

    public boolean getPaused() {
        return model.paused;
    }

    //Dummy methods
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}

    //Ball actions
    public void mouseReleased(MouseEvent e) {
        model.ballReleased(e.getX(), e.getY());

    }
    public void mousePressed(MouseEvent e) {
        model.setBallClicked(e.getX(), e.getY());
    }

    //Key actions
    public void keyTyped(KeyEvent e) {
        switch(Character.toLowerCase(e.getKeyChar())) {
            case 'p':
                actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Pause"));
                break;
            case 's':
                actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Save"));
                break;
            case 'l':
                actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Load"));
                break;
            case 'r':
                actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Reset"));
                break;
            default:
        }
    }


    //Perform appropriate button action
    public void actionPerformed(ActionEvent e) {
        boolean prevPaused = model.paused;
        final JFileChooser fc = new JFileChooser("./");
        switch(e.getActionCommand()) {
            case "Pause":
                model.paused = !model.paused;
                break;
            case "Save":
                model.paused = true;
                if (fc.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
                    model.saveGame(fc.getSelectedFile());
                }
                model.paused = prevPaused;
                break;
            case "Load":
                model.paused = true;
                if (fc.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
                    model.loadGame(fc.getSelectedFile());
                }
                model.paused = prevPaused;
                break;
            case "Reset":
                model.loadLevel(model.getLevel());
                break;
            case "Instructions":
                model.paused = true;
                view.displayInstructions();
                model.paused = prevPaused;
                break;
            case "Scores":
                model.paused = true;
                model.showScores();
                model.paused = prevPaused;
                break;
            case "About":
                model.paused = true;
                JOptionPane.showMessageDialog(null, "CSCE 3193H Honors Project\n\nAuthor: Brian Roden\nTerm: Spring 2021");
                model.paused = prevPaused;
                break;
            case "Exit":
                System.exit(0);
                break;
            default:
                System.out.println("Default button action");
        }
    }

    public static void main(String args[]) {
        new Controller();            
    }
}
