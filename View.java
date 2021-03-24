import java.awt.Graphics;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;

public class View extends JFrame implements ActionListener {
    Model model;

    //Initialize main window
    View(Controller c, Model m) {
        model = m;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 800);

        initMenu(c);

        MyPanel panel = new MyPanel(c);
        add(panel);

        setVisible(true);
    }

    //Create top menu bar. Used by contructor.
    private void initMenu(Controller c) {
        JMenuBar menuBar = new JMenuBar();
        add(menuBar);

        //Game options menu
        JMenu gameMenu = new JMenu("Game Options");
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
        JMenuItem pauseItem = new JMenuItem("Pause");
        pauseItem.addActionListener(c);
        gameMenu.add(pauseItem);
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(c);
        gameMenu.add(saveItem);
        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.addActionListener(c);
        gameMenu.add(loadItem);

        //Help menu
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);
        JMenuItem instructionsItem = new JMenuItem("Instructions");
        instructionsItem.addActionListener(c);
        helpMenu.add(instructionsItem);
    }

    public void displayInstructions() {
        //TODO: more descriptive
        String instructions = "Text goes here";
        JOptionPane.showMessageDialog(this, instructions, "Instructions", JOptionPane.INFORMATION_MESSAGE);
    }
    
    //TODO: This can probably be cut
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action detected!");
        repaint();
    }

    //Main panel
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
}
