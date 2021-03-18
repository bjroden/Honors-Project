import java.awt.Graphics;
import javax.swing.*;

public class View extends JFrame {
    Model model;

    View(Controller c, Model m) {
        model = m;

        MyPanel panel = new MyPanel(c);
        add(panel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 800);
        setVisible(true);
    }
    
    private class MyPanel extends JPanel {
        Controller controller;
        MyPanel(Controller c) {
            super();
            controller = c;
        }

        @Override public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawLine(0, 0, getWidth(), getHeight());
        }
    }
}
