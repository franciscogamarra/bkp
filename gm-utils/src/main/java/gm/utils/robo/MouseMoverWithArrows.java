package gm.utils.robo;

import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class MouseMoverWithArrows {

    private static final Robot robot = Robo.robot;

    public static void moveMouseBy(int x, int y) {
        int currentX = (int) MouseInfo.getPointerInfo().getLocation().getX();
        int currentY = (int) MouseInfo.getPointerInfo().getLocation().getY();
        robot.mouseMove(currentX + x, currentY + y);
    }

    public static void startListening() {
        JFrame frame = new JFrame();
        frame.setSize(1, 1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        moveMouseBy(0, -1);  // Move up
                        break;
                    case KeyEvent.VK_DOWN:
                        moveMouseBy(0, 1);  // Move down
                        break;
                    case KeyEvent.VK_LEFT:
                        moveMouseBy(-1, 0);  // Move left
                        break;
                    case KeyEvent.VK_RIGHT:
                        moveMouseBy(1, 0);  // Move right
                        break;
                }
            }
        });
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        startListening();
    }
}
