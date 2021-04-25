import java.awt.Graphics;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.MouseInfo;

public class View extends JFrame {
    Model model;

    //Initialize main window
    View(Controller c, Model m) {
        model = m;
        addKeyListener(c);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //TODO:
        setSize(Model.mapWidth, Model.mapHeight + 20);
        setResizable(false);
        setTitle("World's Hardest Golf");

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
        JMenuItem resetItem = new JMenuItem("Reset");
        resetItem.addActionListener(c);
        gameMenu.add(resetItem);

        //Help menu
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);
        JMenuItem instructionsItem = new JMenuItem("Instructions");
        instructionsItem.addActionListener(c);
        helpMenu.add(instructionsItem);
        JMenuItem scoresItem = new JMenuItem("Scores");
        scoresItem.addActionListener(c);
        helpMenu.add(scoresItem);
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(c);
        helpMenu.add(aboutItem);
    }

    public void displayInstructions() {
        String instructions = "How to play:\n\nClick and drag the ball to launch it. Try to complete the level in as few strokes as possible.\n\nAll targets must be hit before reaching the goal, which will turn from yellow to green when you can exit the level.\n\nRed squares reset the ball to the start, black squares are walls, and arrows are boost pads.";
        JOptionPane.showMessageDialog(this, instructions, "Instructions", JOptionPane.INFORMATION_MESSAGE);
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

            //Draw sprites
            Iterator<Sprite> iter = model.getSprites().iterator();
            while(iter.hasNext()) {
                iter.next().drawSprite(g);
            }
            //Draw ball, and line if being held
            Ball ball = model.getBall();
            ball.drawSprite(g);
            if (model.getBallClicked()) {
                int mouseX = (int) (- getLocationOnScreen().getX() + MouseInfo.getPointerInfo().getLocation().getX());
                int mouseY = (int) (- getLocationOnScreen().getY() + MouseInfo.getPointerInfo().getLocation().getY());
                int ballXCenter = ball.getX() + (ball.getWidth() / 2);
                int ballYCenter = ball.getY() + (ball.getHeight() / 2);
                g.drawLine(ballXCenter, ballYCenter, mouseX, mouseY);
            }
        }
    }

}
