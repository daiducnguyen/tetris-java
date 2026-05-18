package tetris;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameEngine engine = new GameEngine();
                GamePanel panel = new GamePanel(engine);

                JFrame frame = new JFrame("Java Tetris");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(true); 

                frame.add(panel);
                panel.addKeyListener(new InputHandler(engine));

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                panel.requestFocusInWindow();
            }
        });
    }
}


