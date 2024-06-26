import javax.swing.*;

public class MainFrame implements Runnable {
    private GraphicsPanel p;
    public static boolean endGame = false;

    public MainFrame (int num) {
        JFrame f = new JFrame("Minesweeper");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(760,842);
        f.setLocation(400,100);

        p = new GraphicsPanel(num);
        f.add(p);

        f.setVisible(true);

        Thread thread = new Thread(this);
        thread.start();
    }

    public static void end() {
        endGame = true;
    }

    public void run() {
        while (!endGame) {
            p.repaint();
        }
        p.repaint();
    }
}