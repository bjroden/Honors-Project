import java.awt.Graphics;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;

public class View extends JFrame implements ActionListener {
    Model model;

    View(Controller c, Model m) {
        model = m;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 800);

        initMenu();

        MyPanel panel = new MyPanel(c);
        add(panel);

        setVisible(true);
    }

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        add(menuBar);

        JMenu gameMenu = new JMenu("Game Options");
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
        JMenuItem pauseItem = new JMenuItem("Pause");
        pauseItem.addActionListener(this);
        gameMenu.add(pauseItem);
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(this);
        gameMenu.add(saveItem);
        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.addActionListener(this);
        gameMenu.add(loadItem);
    }
    
    private class MyPanel extends JPanel {
        Controller controller;
        MyPanel(Controller c) {
            controller = c;
            setOpaque(true);
            setBackground(Color.cyan);
            addMouseListener(controller);
        }

        @Override public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawLine(0, 0, getWidth(), getHeight());
        }
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Action detected!");
        repaint();
    }
}
